package com.yxm.customview.bezier

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
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

    private val mPointPaint = Paint()
    private var mFixPoint: PointF? = null
    private var mDragPointF: PointF? = null
    private var mFixPointMaxRadius = 7
    private var mFixPointMinRadius = 3
    private var mFixPointRadius = 0
    private var mDragPointRadius = 10

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
            canvas?.drawPath(bezierPath,mPointPaint)
        }
        //3.拖动圆，大小不变
        canvas?.drawCircle(mDragPointF!!.x, mDragPointF!!.y, mDragPointRadius.toFloat(), mPointPaint)
        //4.画贝塞尔曲线
    }

    /**
     * 求贝塞尔曲线
     */
    private fun getBezierPath(): Path? {
        val bezierPath = Path()
        val controlPoint = getControlPoint()

        val distance = getPointDistance(mFixPoint!!, mDragPointF!!)
        mFixPointRadius = (mFixPointMaxRadius - distance.toInt() / 14)
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val downX = event.x
                val downY = event.y
                initPoint(downX, downY)
            }
            MotionEvent.ACTION_MOVE -> {
                val moveX = event.x
                val moveY = event.y
                updateDragPoint(moveX, moveY)
            }
            MotionEvent.ACTION_UP -> {

            }
        }
        invalidate()
        return true
    }

    private fun initPoint(downX: Float, downY: Float) {
        mFixPoint = PointF(downX, downY)
        mDragPointF = PointF(downX, downY)
    }

    private fun updateDragPoint(moveX: Float, moveY: Float) {
        mDragPointF!!.x = moveX
        mDragPointF!!.y = moveY
    }

    /**
     * 获取控制点
     */
    private fun getControlPoint(): PointF {
        val controlPoint = PointF()
        controlPoint.x = (mDragPointF!!.x + mFixPoint!!.x) / 2
        controlPoint.y = (mDragPointF!!.y + mFixPoint!!.y) / 2
        return controlPoint
    }

}