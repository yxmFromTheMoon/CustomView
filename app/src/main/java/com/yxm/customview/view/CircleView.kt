package com.yxm.customview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/8/30 15:41
 * @description
 */
class CircleView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyle: Int = 0)
    : View(context, attr, defStyle) {

    private var mPaint: Paint = Paint()
    private var mColor: Int = 0

    init {
        mPaint.isAntiAlias = true
        mPaint.isDither = true
    }


    override fun onDraw(canvas: Canvas?) {
        val cx = width.toFloat() / 2
        val cy = height.toFloat() / 2
        canvas?.drawCircle(cx, cy, cx, mPaint)
    }

    fun exchangeColor(color: Int) {
        mColor = color
        mPaint.color = color
        invalidate()
    }

    fun getColor(): Int {
        return mColor
    }

}