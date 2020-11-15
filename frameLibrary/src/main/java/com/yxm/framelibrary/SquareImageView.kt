package com.yxm.framelibrary

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * Created by Myron at 2020/11/15 13:21
 * @email yxmbest@163.com
 * @description
 */
class SquareImageView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0)
    : AppCompatImageView(context, attributeSet, defStyle) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        // 设置宽高为一样
        setMeasuredDimension(width, width)
    }

}