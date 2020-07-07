package com.yxm.customview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.yxm.customview.R
import com.yxm.customview.utils.Utils

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/7/2 20:55
 * @Description
 */
class LetterSideBar @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null,
                                              defStyle: Int = 0) : View(context, attributeSet, defStyle) {
    private var mLetterPaint: Paint
    private var mHighLightPaint:Paint
    private var mLetterSize = 15f
    private var mLetterColor = Color.BLACK
    private var mHighLightLetterColor = Color.RED
    private var mCurrentLetter: String? = null
    private val mLetters = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#")
    private var mListener: OnLettersSelected? = null

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.LetterSideBar)
        mLetterSize = typedArray.getDimension(R.styleable.LetterSideBar_letterSize, Utils.sp2px(this, mLetterSize).toFloat())
        mLetterColor = typedArray.getColor(R.styleable.LetterSideBar_letterColor, mLetterColor)
        mHighLightLetterColor = typedArray.getColor(R.styleable.LetterSideBar_highLightLetterColor,mHighLightLetterColor)
        typedArray.recycle()

        mLetterPaint = Paint()
        mLetterPaint.isAntiAlias = true
        mLetterPaint.color = mLetterColor
        mLetterPaint.textSize = mLetterSize

        mHighLightPaint = Paint()
        mHighLightPaint.isAntiAlias = true
        mHighLightPaint.color = mHighLightLetterColor
        mHighLightPaint.textSize = mLetterSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val letterWidth = mLetterPaint.measureText("A").toInt()
        val width = paddingLeft + paddingRight + letterWidth
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val itemHeight = (height - paddingBottom - paddingTop) / mLetters.size
        for ((index, _) in mLetters.withIndex()) {
            val fontMetrics = mLetterPaint.fontMetrics
            val dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
            //计算每个字母的中心位置，字母高度的一半 + 前面字母个数的高度
            val letterCenterY = itemHeight / 2 + index * itemHeight
            val baseLine = letterCenterY + dy
            val letterWidth = mLetterPaint.measureText(mLetters[index]).toInt()
            val dx = (width - letterWidth) / 2

            if (mLetters[index] == mCurrentLetter) {
                //高亮
                canvas?.drawText(mLetters[index], dx.toFloat(), baseLine, mHighLightPaint)
            } else {
                canvas?.drawText(mLetters[index], dx.toFloat(), baseLine, mLetterPaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE -> {
                //计算出当前的字母
                val currentMoveY = event.y
                //currentMoveY / 每个字母的高度 拿到索引
                val itemHeight = (height - paddingBottom - paddingTop) / mLetters.size
                var currentPosition = (currentMoveY / itemHeight).toInt()
                if (currentPosition < 0) {
                    currentPosition = 0
                }
                if (currentPosition > mLetters.size - 1) {
                    currentPosition = mLetters.size - 1
                }
                if (mCurrentLetter == mLetters[currentPosition]) {
                    return true
                } else {
                    mCurrentLetter = mLetters[currentPosition]
                }

                mListener?.selected(mCurrentLetter)
                //重新绘制
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                postDelayed({
                    mListener?.selected(null)
                }, 500)
            }
        }
        return true
    }

    fun setOnLettersSelectedListener(listener: OnLettersSelected) {
        mListener = listener
    }

    interface OnLettersSelected {
        fun selected(letter: String?)
    }
}