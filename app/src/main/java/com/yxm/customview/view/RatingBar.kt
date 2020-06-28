package com.yxm.customview.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.yxm.customview.R
import java.lang.RuntimeException

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/27 19:12
 * @Description RatingBar
 */
class RatingBar @JvmOverloads
constructor(context: Context, attributeSet: AttributeSet? = null
            , defStyle: Int = 0) : View(context, attributeSet, defStyle) {

    private var mStarNormalBitmap: Bitmap
    private var mStarFocusBitmap: Bitmap
    private var mGradeNumbers = 5
    private var mCurrentGrade = 0
    private var mGap = 20

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.RatingBar)
        val starNormalId = typedArray.getResourceId(R.styleable.RatingBar_starNormal, 0)
        if (starNormalId == 0) {
            throw RuntimeException("请设置属性starNormal")
        }
        mStarNormalBitmap = BitmapFactory.decodeResource(resources, starNormalId)
        val starFocusId = typedArray.getResourceId(R.styleable.RatingBar_starFocus, 0)
        if (starFocusId == 0) {
            throw RuntimeException("请设置属性starFocus")
        }
        mStarFocusBitmap = BitmapFactory.decodeResource(resources, starFocusId)
        mGradeNumbers = typedArray.getInt(R.styleable.RatingBar_gradeNumbers, mGradeNumbers)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //TODO 加padding
        val height = mStarNormalBitmap.height
        val width = mStarNormalBitmap.width * mGradeNumbers + mGap * (mGradeNumbers - 1)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        //super.onDraw(canvas)
        for (i in 0 until mGradeNumbers) {
            //i * 星星的宽度
            val dx = i * mStarNormalBitmap.width.toFloat()
            if (mCurrentGrade > i) {
                canvas?.drawBitmap(mStarFocusBitmap, dx + i * mGap, 0f, null)
            } else {
                canvas?.drawBitmap(mStarNormalBitmap, dx + i * mGap, 0f, null)
            }

        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //移动、按下、抬起处理逻辑一样，判断手指的位置，根据当前位置区刷新显示
        when (event?.action) {
            MotionEvent.ACTION_MOVE,
            MotionEvent.ACTION_DOWN -> {
                //getRawX()获得相对于屏幕的位置
                val moveX = event.x //event.getX()获取相对于当前控件的位置
                var currentGrade = (moveX / (mStarNormalBitmap.width + mGap) + 1).toInt()
                if (currentGrade < 0) {
                    currentGrade = 0
                }
                if (currentGrade > mGradeNumbers) {
                    currentGrade = mGradeNumbers
                }
                //分数相同不刷新
                if (mCurrentGrade == currentGrade) {
                    return true
                }
                mCurrentGrade = currentGrade
                //刷新显示
                invalidate()
            }
        }
        return true
    }
}