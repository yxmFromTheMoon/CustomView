package com.yxm.framelibrary

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Created by Myron at 2020/11/15 13:23
 * @email yxmbest@163.com
 * @description
 */
class SquareFrameLayout @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0)
    : FrameLayout(context, attributeSet, defStyle) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        // 设置宽高为一样
        setMeasuredDimension(width, width)
    }

}