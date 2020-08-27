package com.yxm.customview.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/8/20 21:13
 * @description 九宫格自定义view
 */
class LockPatterView @JvmOverloads constructor(context: Context,
                                               attributeSet: AttributeSet? = null, defStyle: Int = 0) : View(context, attributeSet, defStyle) {
    //是否初始化，确保只初始化一次
    private var mIsInit = false

    override fun onDraw(canvas: Canvas?) {
        //初始化九宫格
        if(!mIsInit){
            initDot()
            initPaint()
            mIsInit = true
        }


        //绘制九个宫格
    }

    private fun initDot() {

    }

    /**
     * 3个点状态的画笔，
     */
    private fun initPaint() {
    }

}