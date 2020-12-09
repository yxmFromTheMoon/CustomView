package com.yxm.customview.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.yxm.customview.dp
import java.lang.IllegalStateException
import kotlin.math.cos
import kotlin.math.sin

/**
 * Created by Myron at 2020/12/7 19:59
 * @email yxmbest@163.com
 * @description 仪表盘刻度view
 */
class DashBoardView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null,
                                              defStyle: Int = 0) : View(context, attributeSet, defStyle) {

    //仪表刻度画笔
    private val mDashPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    //最外圈的圆弧画笔
    private val mOuterArcPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    //指针画笔
    private val mPointerPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    //指针指向的位置
    private var mPointerPosition = 1

    //圆弧开口角度
    private var mOpenAngle = 120f
    private var mOuterArcRadius = 150f.dp
    private var mPointerLength = 120f.dp

    //刻度线的矩形长
    private var mDashLength = 10f.dp

    //刻度线的矩形宽
    private var mDashWidth = 2f.dp
    private var mDashPath = Path()
    private val mOuterArcPath = Path()
    private lateinit var mDashPathEffect: PathDashPathEffect

    //仪表盘刻度数
    private var mDashCount = 20

    init {
        mOuterArcPaint.color = Color.BLACK
        mOuterArcPaint.style = Paint.Style.STROKE
        mOuterArcPaint.strokeWidth = 3f.dp
        mDashPath.addRect(0f, 0f, mDashWidth, mDashLength, Path.Direction.CCW)
        mDashPaint.color = Color.RED
        mPointerPaint.color = Color.BLACK
        mPointerPaint.strokeWidth = 5f.dp
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mOuterArcPath.reset()
        mOuterArcPath.addArc(width / 2 - mOuterArcRadius, height / 2 - mOuterArcRadius,
                width / 2 + mOuterArcRadius, height / 2 + mOuterArcRadius, 90 + mOpenAngle / 2,
                360 - mOpenAngle)
        val pathMeasure = PathMeasure(mOuterArcPath, false)
        mDashPathEffect = PathDashPathEffect(mDashPath, (pathMeasure.length - mDashWidth) / mDashCount, 0f, PathDashPathEffect.Style.ROTATE)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        mDashPaint.pathEffect = mDashPathEffect
        canvas.drawPath(mOuterArcPath, mOuterArcPaint)

        canvas.drawArc(width / 2 - mOuterArcRadius, height / 2 - mOuterArcRadius,
                width / 2 + mOuterArcRadius, height / 2 + mOuterArcRadius, 90 + mOpenAngle / 2,
                360 - mOpenAngle, false, mDashPaint)

        val arcAngle = 360 - mOpenAngle
        val eachAngle = arcAngle / mDashCount
        val targetAngle = mOpenAngle / 2 + 90 + eachAngle * mPointerPosition

        canvas.drawLine((width / 2).toFloat(), (height / 2).toFloat(), getPointerX(targetAngle),
                getPointerY(targetAngle), mPointerPaint)
    }

    /**
     * 获取指针的终止点x坐标
     * @param targetAngle Float
     * @return Float
     */
    private fun getPointerX(targetAngle: Float): Float {
        return (width / 2 + mPointerLength * cos(Math.toRadians(targetAngle.toDouble()))).toFloat()
    }
    /**
     * 获取指针的终止点y坐标
     * @param targetAngle Float
     * @return Float
     */
    private fun getPointerY(targetAngle: Float): Float {
        return (height / 2 + mPointerLength * sin(Math.toRadians(targetAngle.toDouble()))).toFloat()
    }

    @Synchronized
    fun setPointerPosition(position:Int){
        if(position < 0 || position > mDashCount){
            throw IllegalStateException("超出仪表范围")
        }
        mPointerPosition = position
        invalidate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clearAnimation()
    }
}