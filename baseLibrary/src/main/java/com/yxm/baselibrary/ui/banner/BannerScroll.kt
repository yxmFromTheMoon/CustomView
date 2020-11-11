package com.yxm.baselibrary.ui.banner

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager

/**
 *@author: yxm
 *@time: 2020/11/11
 *@description:
 */
class BannerScroll @JvmOverloads constructor(context: Context, interpolator: Interpolator? = null, flywheel: Boolean = false)
    : Scroller(context, interpolator, flywheel) {

    private var mDefaultScrollDuration = 1000L

    fun setScrollDuration(duration: Long) {
        mDefaultScrollDuration = duration
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, mDefaultScrollDuration.toInt())
    }

}