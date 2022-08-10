package com.yxm.customview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import com.yxm.customview.dp
import com.yxm.customview.utils.Utils
import kotlin.math.max
import kotlin.math.min

/**
 * Created by yxm at 2020/12/16 21:05
 * @email: yxmbest@163.com
 * @description: ScalableImageView
 */
class ScalableImageView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null)
    : View(context, attributeSet) {
    companion object {
        val IMAGE_SIZE = 300.dp
        const val SCALE_FACTOR = 1.3f
    }

    private var offsetX = 0f
    private var offsetY = 0f
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var smallScale = 0f
    private var bigScale = 0f
    private var currentScale = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var isBigMode = false
    private val scroller = OverScroller(context)
    private val gestureDetector = GestureDetectorCompat(context, MyGestureDetector())
    private val refreshRunnable = RefreshRunnable()
    private val scaleGestureDetector: ScaleGestureDetector = ScaleGestureDetector(context, MyScaleGestureDetector())
    private var bitmap: Bitmap = Utils.getBitmap(context, IMAGE_SIZE.toInt())

    private val scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", smallScale, bigScale)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (width - IMAGE_SIZE) / 2
        originalOffsetY = (height - IMAGE_SIZE) / 2

        if (bitmap.width / bitmap.height.toFloat() > width / height.toFloat()) {
            smallScale = width / bitmap.width.toFloat()
            bigScale = height / bitmap.height.toFloat() * SCALE_FACTOR
        } else {
            smallScale = height / bitmap.height.toFloat()
            bigScale = width / bitmap.width.toFloat() * SCALE_FACTOR
        }
        currentScale = smallScale
        scaleAnimator.setFloatValues(smallScale, bigScale)
    }

    override fun onDraw(canvas: Canvas) {
        val scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        canvas.scale(currentScale, currentScale, (width / 2).toFloat(), (height / 2).toFloat())
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val result = scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress) {
            gestureDetector.onTouchEvent(event)
        }
        return result
    }

    private fun fixOffset() {
        offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2)
        offsetX = max(offsetX, -(bitmap.width * bigScale - width) / 2)
        offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2)
        offsetY = max(offsetY, -(bitmap.height * bigScale - height) / 2)
    }

    inner class RefreshRunnable : Runnable {

        override fun run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                postOnAnimation(refreshRunnable)
            }
        }
    }

    inner class MyGestureDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            if (isBigMode) {
                scroller.fling(offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(), ((-(bitmap.width * bigScale - width) / 2).toInt()),
                        ((bitmap.width * bigScale - width) / 2).toInt(), ((-(bitmap.height * bigScale - height) / 2).toInt()),
                        ((bitmap.height * bigScale - height) / 2).toInt(), 30.dp.toInt(), 30.dp.toInt())
                postOnAnimation(refreshRunnable)
            }
            return false
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            if (isBigMode) {
                offsetX -= distanceX
                offsetY -= distanceY
                fixOffset()
                invalidate()
            }
            return false
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            isBigMode = !isBigMode
            if (isBigMode) {
                offsetX = (e.x - width / 2) * (1 - bigScale / smallScale)
                offsetY = (e.x - height / 2) * (1 - bigScale / smallScale)
                fixOffset()
                scaleAnimator.start()
            } else {
                scaleAnimator.reverse()
            }
            return true
        }
    }

    inner class MyScaleGestureDetector : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val tempScale = currentScale * detector.scaleFactor
            return if (tempScale < smallScale || tempScale > bigScale) {
                false
            } else {
                currentScale *= detector.scaleFactor
                true
            }
        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            offsetX = (detector.focusX - width / 2) * (1 - bigScale / smallScale)
            offsetY = (detector.focusY - height / 2) * (1 - bigScale / smallScale)
            return super.onScaleBegin(detector)
        }
    }
}