package com.yxm.customview.utils

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/20 22:12
 * @Description
 */
object Utils {

    @JvmStatic
    fun sp2px(view: View,sp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, view.resources.displayMetrics).toInt()
    }

    @JvmStatic
    fun dp2px(context:Context,dp:Float):Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.resources.displayMetrics).toInt()
    }

    @JvmStatic
    fun intValueAnimator(start:Int,end:Int,duration:Long,listener:ValueAnimator.AnimatorUpdateListener){
        val animator = ObjectAnimator.ofInt(start,end)
        animator.duration = duration
        animator.addUpdateListener(listener)
        animator.start()
    }

    @JvmStatic
    fun floatValueAnimator(start:Float,end:Float,duration:Long,listener:ValueAnimator.AnimatorUpdateListener){
        val animator = ObjectAnimator.ofFloat(start,end)
        animator.duration = duration
        animator.addUpdateListener(listener)
        animator.start()
    }

    @JvmStatic
    fun getScreenWidth(context: Context):Int{
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }
}