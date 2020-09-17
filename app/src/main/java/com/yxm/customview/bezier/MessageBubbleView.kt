package com.yxm.customview.bezier

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.OvershootInterpolator
import com.yxm.customview.utils.Utils
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 *@author lm
 *@time 2020/9/16 2:11 PM
 *@description
 */
class MessageBubbleView @JvmOverloads constructor(context: Context, attributes: AttributeSet? = null, defStyle: Int = 0)
    : View(context, attributes, defStyle) {

    private var mBitmap: Bitmap? = null
    private val mPointPaint = Paint()
    private var mFixPoint: PointF? = null
    private var mDragPointF: PointF? = null
    private var mFixPointMaxRadius = 7
    private var mFixPointMinRadius = 3
    private var mFixPointRadius = 0
    private var mDragPointRadius = 10

    private var mListener: MessageBubbleListener? = null

    fun setMessageBubbleListener(listener: MessageBubbleListener) {
        mListener = listener
    }

    init {
        mPointPaint.isAntiAlias = true
        mPointPaint.isDither = true
        mPointPaint.color = Color.RED
        mPointPaint.style = Paint.Style.FILL
        mFixPointMinRadius = Utils.dp2px(context, mFixPointMinRadius.toFloat())
        mFixPointMaxRadius = Utils.dp2px(context, mFixPointMaxRadius.toFloat())
        mDragPointRadius = Utils.dp2px(context, mDragPointRadius.toFloat())

    }

    private fun getPointDistance(point1: PointF, point2: PointF): Float {
        return sqrt((point2.x - point1.x) * (point2.x - point1.x) + (point2.y - point1.y) * (point2.y - point1.y))
    }

    override fun onDraw(canvas: Canvas?) {
        if (mFixPoint == null || mDragPointF == null) {
            return
        }

        //1.画固定的圆
        //2.固定的圆的半径会随着与拖动圆的距离慢慢变小，直到不见（不绘制）
        val bezierPath = getBezierPath()
        if (bezierPath != null) {
            canvas?.drawCircle(mFixPoint!!.x, mFixPoint!!.y, mFixPointRadius.toFloat(), mPointPaint)
            canvas?.drawPath(bezierPath, mPointPaint)
        }
        //3.拖动圆，大小不变
        canvas?.drawCircle(mDragPointF!!.x, mDragPointF!!.y, mDragPointRadius.toFloat(), mPointPaint)
        mBitmap?.let {
            canvas?.drawBitmap(it, mDragPointF!!.x - it.width / 2, mDragPointF!!.y - it.height / 2, null)
        }
    }

    /**
     * 求贝塞尔曲线
     */
    private fun getBezierPath(): Path? {
        val bezierPath = Path()
        val controlPoint = getControlPoint()

        val distance = getPointDistance(mFixPoint!!, mDragPointF!!)
        mFixPointRadius = (mFixPointMaxRadius - distance.toInt() / 28)
        if (mFixPointRadius < mFixPointMinRadius) {
            return null
        }
        //1.求四个点的坐标，求角度
        val tanA = (mDragPointF!!.y - mFixPoint!!.y) / (mDragPointF!!.x - mFixPoint!!.x)
        //角
        val degreeA = atan(tanA.toDouble())

        val p0x = mFixPoint!!.x + mFixPointRadius * sin(degreeA)
        val p0y = mFixPoint!!.y - mFixPointRadius * cos(degreeA)

        val p1x = mDragPointF!!.x + mDragPointRadius * sin(degreeA)
        val p1y = mDragPointF!!.y - mDragPointRadius * cos(degreeA)

        val p2x = mDragPointF!!.x - mDragPointRadius * sin(degreeA)
        val p2y = mDragPointF!!.y + mDragPointRadius * cos(degreeA)

        val p3x = mFixPoint!!.x - mFixPointRadius * sin(degreeA)
        val p3y = mFixPoint!!.y + mFixPointRadius * cos(degreeA)
        bezierPath.moveTo(p0x.toFloat(), p0y.toFloat())
        bezierPath.quadTo(controlPoint.x, controlPoint.y, p1x.toFloat(), p1y.toFloat())
        bezierPath.lineTo(p2x.toFloat(), p2y.toFloat())
        bezierPath.quadTo(controlPoint.x, controlPoint.y, p3x.toFloat(), p3y.toFloat())
        bezierPath.close()
        return bezierPath
    }

    fun initPoint(downX: Float, downY: Float) {
        mFixPoint = PointF(downX, downY)
        mDragPointF = PointF(downX, downY)
    }

    fun updateDragPoint(moveX: Float, moveY: Float) {
        mDragPointF!!.x = moveX
        mDragPointF!!.y = moveY
        invalidate()
    }

    /**
     * 获取控制点
     */
    fun getControlPoint(): PointF {
        val controlPoint = PointF()
        controlPoint.x = (mDragPointF!!.x + mFixPoint!!.x) / 2
        controlPoint.y = (mDragPointF!!.y + mFixPoint!!.y) / 2
        return controlPoint
    }

    //处理手指松开
    fun releaseBubbleView() {
        if (mFixPointRadius >= mFixPointMinRadius) {
            //回弹
            val animator = ObjectAnimator.ofFloat(1f)
            animator.duration = 350
            val start = PointF(mDragPointF!!.x, mDragPointF!!.y)
            val end = PointF(mFixPoint!!.x, mFixPoint!!.y)
            animator.addUpdateListener {
                val percent = it.animatedValue as Float
                val point = Utils.getPointByPercent(start, end, percent)
                updateDragPoint(point.x, point.y)
            }
            animator.interpolator = OvershootInterpolator()
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    mListener?.restore()
                }
            })
            animator.start()
            //通知TouchListener 移除当前view，显示原来的view
        } else {
            //爆炸
            mListener?.dismiss(mDragPointF)
        }
    }

    fun setBitmap(bitmap: Bitmap) {
        mBitmap = bitmap
    }

    interface MessageBubbleListener {
        fun restore()

        fun dismiss(pointF: PointF?)
    }

    companion object {
        fun attach(view: View, listener: BubbleViewTouchListener.BubbleDisappearListener) {
            view.setOnTouchListener(BubbleViewTouchListener(view, view.context,listener))
        }
    }

}