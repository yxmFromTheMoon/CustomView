package com.yxm.customview.bezier

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.PointF
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.yxm.customview.R
import com.yxm.customview.invisible
import com.yxm.customview.utils.Utils
import com.yxm.customview.visible

class BubbleViewTouchListener(view: View, context: Context, listener: BubbleDisappearListener)
    : View.OnTouchListener, MessageBubbleView.MessageBubbleListener {

    private var mStaticView = view
    private var mMessageBubbleView: MessageBubbleView
    private var mContext = context
    private var mWindowManager: WindowManager
    private var mParams: WindowManager.LayoutParams
    private var mBombFrameLayout: FrameLayout
    private var mBombImageView: ImageView
    var mDisappearListener: BubbleDisappearListener? = listener

    init {
        mWindowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mParams = WindowManager.LayoutParams()
        //window背景要透明
        mParams.format = PixelFormat.TRANSPARENT
        mMessageBubbleView = MessageBubbleView(mContext)
        mMessageBubbleView.setMessageBubbleListener(this)
        mBombFrameLayout = FrameLayout(mContext)

        mBombImageView = ImageView(mContext)
        mBombImageView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT)
        mBombFrameLayout.addView(mBombImageView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                //初始化点
                mWindowManager.addView(mMessageBubbleView, mParams)
                //保证固定圆的中心在view的中心
                val location = IntArray(2)
                val bitmap = convertViewToBitmap(mStaticView)
                mStaticView.getLocationOnScreen(location)
                mMessageBubbleView.setBitmap(bitmap)
                mMessageBubbleView.initPoint(location[0].toFloat() + mStaticView.width / 2, location[1].toFloat() + mStaticView.height / 2 - Utils.getStatusBarHeight(mContext))
                //先把自己隐藏，优化搞一个渐变动画
                mStaticView.invisible()
            }
            MotionEvent.ACTION_MOVE -> {
                mMessageBubbleView.updateDragPoint(event.rawX, event.rawY - Utils.getStatusBarHeight(mContext))
            }
            MotionEvent.ACTION_UP -> {
                mMessageBubbleView.releaseBubbleView()
            }
        }
        return true
    }

    private fun convertViewToBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    override fun restore() {
        //把消息的view移除
        mWindowManager.removeView(mMessageBubbleView)
        mStaticView.visible()
    }

    override fun dismiss(pointF: PointF?) {
        mWindowManager.removeView(mMessageBubbleView)
        //要在windowManager上添加一个爆炸动画
        mWindowManager.addView(mBombFrameLayout, mParams)
        //帧动画实现爆炸效果
        mBombImageView.setBackgroundResource(R.drawable.anim_bubble_pop)
        val drawable = mBombImageView.background as AnimationDrawable
        mBombImageView.x = pointF!!.x - drawable.intrinsicWidth / 2
        mBombImageView.y = pointF.y - drawable.intrinsicHeight / 2
        drawable.start()
        //动画完成后移除爆炸view
        mBombImageView.postDelayed({
            mWindowManager.removeView(mBombFrameLayout)
            //通知外面消失
            mDisappearListener?.dismiss()
        }, getAnimatorDrawableTime(drawable))
    }

    private fun getAnimatorDrawableTime(drawable: AnimationDrawable): Long {
        val numberOfFrame = drawable.numberOfFrames
        var time: Long = 0
        for (i in 0 until numberOfFrame) {
            time += drawable.getDuration(i)
        }
        return time
    }

    interface BubbleDisappearListener {
        fun dismiss()
    }

}
