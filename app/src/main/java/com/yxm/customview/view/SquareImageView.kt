package com.yxm.customview.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import java.lang.Integer.min

/**
 * Created by Myron at 2020/12/12 21:27
 * @email yxmbest@163.com
 * @description
 */
class SquareImageView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null)
    : AppCompatImageView(context, attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val min = width.coerceAtMost(height)

        setMeasuredDimension(min,min)
    }
}