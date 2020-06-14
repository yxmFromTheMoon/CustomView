package com.yxm.customview.basic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.yxm.customview.R

/**
 * @author yxm
 * 2020/5/31 21:51
 * @function 自定义TextView
 */
class TextView @JvmOverloads constructor
(context: Context, attributes: AttributeSet? = null, defStyle: Int = 0) : View(context, attributes) {

    private var mTextSize = 15
    private var mTextColor = Color.BLACK
    private var mText: String
    private var mPaint: Paint = Paint()

    init {
        val typedArray = context.obtainStyledAttributes(attributes,R.styleable.TextView)
        mText = typedArray.getString(R.styleable.TextView_customText).toString()
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.TextView_customTextSize, sp2px(mTextSize.toFloat()))
        mTextColor = typedArray.getColor(R.styleable.TextView_customTextColor, mTextColor)
        typedArray.recycle()
        initPaint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        //1.确定的值，不需要计算,给的多少就是多少
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        //2.wrap_content,需要计算
        if(widthMode == MeasureSpec.AT_MOST){
            //计算的宽度,宽度与字体的长度有关，与字体的大小有关
            val bounds = Rect()
            //获取文本的矩阵
            mPaint.getTextBounds(mText,0, mText.length,bounds)
            width = bounds.width()
        }
        if(heightMode == MeasureSpec.AT_MOST){
            val bounds = Rect()
            mPaint.getTextBounds(mText,0, mText.length,bounds)
            height = bounds.height()
        }

        //3.match_content

        //设置宽高
        setMeasuredDimension(width,height)
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {

            }
            MotionEvent.ACTION_MOVE -> {

            }
            MotionEvent.ACTION_UP -> {

            }
        }

        invalidate()
        return super.onTouchEvent(event)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        canvas?.translate((mWidth / 2).toFloat(),(mHeight / 2).toFloat())
        //dy代表的是：高度的一半到baseLine的距离
        val fontMetrics = mPaint.fontMetrics
        val dy = (fontMetrics.top - fontMetrics.bottom) / 2 - fontMetrics.bottom
        val baseLine = height / 2 + dy
        //x 开始的位置
        //y 基线
        canvas?.drawText(mText,0f,baseLine,mPaint)

    }
    
    private fun initPaint() {
        mPaint.isAntiAlias = true
        mPaint.color = mTextColor
        mPaint.textSize = mTextSize.toFloat()
    }

    private fun sp2px(sp:Float):Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp,resources.displayMetrics).toInt()
    }
}