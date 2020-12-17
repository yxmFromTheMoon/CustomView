package com.yxm.customview.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.yxm.customview.dp
import com.yxm.customview.utils.Utils

/**
 * Created by yxm at 2020/12/16 21:05
 * @email: yxmbest@163.com
 * @description: ScalableImageView
 */
class ScalableImageView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null)
    : View(context, attributeSet), GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    companion object {
        val IMAGE_SIZE = 300.dp
    }

    private var offsetX = 0f
    private var offsetY = 0f
    private var smallScale = 0
    private var bigScale = 0
    private var isBigMode = false

    private val gestureDetector = GestureDetector(context, this)
    private var bitmap: Bitmap = Utils.getBitmap(context, IMAGE_SIZE.toInt())


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        offsetX = (width - IMAGE_SIZE) / 2
        offsetY = (height - IMAGE_SIZE) / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, offsetX, offsetY, null)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }
}