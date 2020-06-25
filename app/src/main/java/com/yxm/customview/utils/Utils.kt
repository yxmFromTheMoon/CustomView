package com.yxm.customview.utils

import android.util.TypedValue
import android.view.View

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
}