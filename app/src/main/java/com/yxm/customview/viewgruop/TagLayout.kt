package com.yxm.customview.viewgruop

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.math.max

/**
 * Created by Myron at 2021/3/7 14:54
 * @email yxmbest@163.com
 * @description
 */
class TagLayout @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null,
                                          defStyle: Int = 0) : ViewGroup(context, attributeSet, defStyle) {

    private val childrenBounds = mutableListOf<Rect>()

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //测量每个子View
        var widthUsed = 0
        var heightUsed = 0
        var lineMaxHeight = 0
        var lineWidthUsed = 0
        //获取自身viewgroup的宽度
        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        for ((index, child) in children.withIndex()) {
            measureChildWithMargins(child, widthMeasureSpec, 0,
                    heightMeasureSpec, heightUsed)
            //处理换行
            if (specWidthMode != MeasureSpec.UNSPECIFIED &&
                    lineWidthUsed + child.measuredWidth > specWidthSize) {

                lineWidthUsed = 0
                heightUsed += lineMaxHeight
                lineMaxHeight = 0
                measureChildWithMargins(child, widthMeasureSpec, 0,
                        heightMeasureSpec, heightUsed)
            }
            if (index >= childrenBounds.size) {
                childrenBounds.add(Rect())
            }
            val childBounds = childrenBounds[index]
            childBounds.set(lineWidthUsed, heightUsed, lineWidthUsed + child.measuredWidth, heightUsed + child.measuredHeight)

            lineWidthUsed += child.measuredWidth
            widthUsed = max(lineWidthUsed, widthUsed)
            lineMaxHeight = max(lineMaxHeight, child.measuredHeight)
        }
        //测量自己的宽高
        val selfWidth = widthUsed
        val selfHeight = lineMaxHeight + heightUsed
        setMeasuredDimension(selfWidth, selfHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((index, child) in children.withIndex()) {
            val childBounds = childrenBounds[index]
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }


}