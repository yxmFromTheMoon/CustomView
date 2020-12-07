package com.yxm.customview.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.media.Image
import android.os.Debug
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import com.yxm.baselibrary.imageloader.ImageLoaderUtils
import com.yxm.baselibrary.ui.banner.BannerAdapter
import com.yxm.customview.R
import com.yxm.customview.startActivity
import com.yxm.customview.startActivityForResult
import com.yxm.framelibrary.BaseSkinActivity
import com.yxm.framelibrary.imagepicker.ImageSelector
import com.yxm.framelibrary.imagepicker.SelectPictureActivity
import kotlinx.android.synthetic.main.activity_test.*

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/21 20:33
 * @description
 */
class TestActivity : BaseSkinActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }

    override fun initView() {
        val animator = ObjectAnimator.ofInt(0,20)
        animator.addUpdateListener {
            val value = it.animatedValue as Int
            dash_view.setPointerPosition(value)
        }
        animator.duration = 1500
        animator.interpolator = DecelerateInterpolator()
        animator.start()
    }

    override fun initListener() {

    }

    override fun initData() {

    }

}