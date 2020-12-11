package com.yxm.customview.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.media.Image
import android.os.Debug
import android.util.Log
import android.view.View
import android.view.animation.*
import android.widget.ImageView
import com.yxm.baselibrary.imageloader.ImageLoaderUtils
import com.yxm.baselibrary.ui.banner.BannerAdapter
import com.yxm.customview.R
import com.yxm.customview.startActivity
import com.yxm.customview.startActivityForResult
import com.yxm.customview.view.TypeEvaluatorView
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
       val animator = ObjectAnimator.ofObject(provinces_view,"mProvince",TypeEvaluatorView.StringEvaluator(),"北京市","澳门特别行政区")
        animator.duration = 3000
        animator.startDelay = 1000
        animator.start()

        val animator1 = ObjectAnimator.ofFloat(camera_view,"flipBottom",0f,60f)

        val animator2 = ObjectAnimator.ofFloat(camera_view,"flipTop",0f,60f)

        val set = AnimatorSet()
        set.playSequentially(animator1,animator2)
        set.duration = 3000
        set.startDelay = 1000
        set.start()
    }

    override fun initListener() {

    }

    override fun initData() {

    }

}