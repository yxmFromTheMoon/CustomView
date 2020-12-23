package com.yxm.customview.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import com.yxm.customview.R
import com.yxm.customview.view.TypeEvaluatorView
import com.yxm.framelibrary.BaseSkinActivity
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
        val animator = ObjectAnimator.ofObject(provinces_view, "mProvince", TypeEvaluatorView.StringEvaluator(), "北京市", "澳门特别行政区")
        animator.duration = 3000
        animator.startDelay = 1000
        animator.start()

        edit_text.isUseFloatLabel = true

    }

    override fun initListener() {

    }

    override fun initData() {

    }

}