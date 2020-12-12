package com.yxm.customview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.yxm.customview.dp

/**
 * Created by Myron at 2020/12/12 14:25
 * @email yxmbest@163.com
 * @description
 */
class MaterialEditText @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null)
    : AppCompatEditText(context, attributeSet) {

    private var isShowFloatLabel = false
    private var mLabelMargin = 20.dp
    private var mLabelTextSize = 12.dp
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mHorizontalOffset = 5.dp
    private var mVerticalOffset = 23.dp
    private val mExtraVerticalOffset = 16.dp

    var isUseFloatLabel = true
        set(value) {
            if (field != value) {
                field = value
                if (field) {
                    setPadding(paddingLeft, (paddingTop + mLabelMargin + mLabelTextSize).toInt(),
                            paddingRight, paddingBottom)
                } else {
                    setPadding(paddingLeft, (paddingTop - mLabelMargin - mLabelTextSize).toInt(), paddingRight, paddingBottom)
                }
            }
        }

    private val showLabelAnimator by lazy {
        ObjectAnimator.ofFloat(this, "floatingLabelFraction", 1f, 0f)
    }

    var floatingLabelFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        mPaint.textSize = mLabelTextSize
        if (isUseFloatLabel) {
            setPadding(paddingLeft, (paddingTop + mLabelMargin + mLabelTextSize).toInt(),
                    paddingRight, paddingBottom)
        }
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        if (isUseFloatLabel) {
            if (text.isNullOrEmpty() && isShowFloatLabel) {
                isShowFloatLabel = false
                //label不显示
                showLabelAnimator.start()
            } else if (!text.isNullOrEmpty() && !isShowFloatLabel) {
                //label显示
                isShowFloatLabel = true
                showLabelAnimator.reverse()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isUseFloatLabel ) {
            mPaint.alpha = (floatingLabelFraction * 0xff).toInt()
            val currentVerticalOffset = mVerticalOffset + mExtraVerticalOffset * (1 - floatingLabelFraction)
            canvas.drawText(hint.toString(), mHorizontalOffset,
                    currentVerticalOffset, mPaint)
        }

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clearAnimation()
    }
}