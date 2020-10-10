package com.yxm.customview.activity

import android.animation.ValueAnimator
import com.yxm.customview.R
import com.yxm.baselibrary.base.BaseActivity
import com.yxm.customview.utils.Utils
import com.yxm.customview.view.QQStepView
import kotlinx.android.synthetic.main.activity_qq_step_view.*

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/27 13:06
 * @Description
 */
class QQStepViewActivity: BaseActivity() {

    private lateinit var qqStepView: QQStepView

    override fun getLayoutId(): Int {
        return R.layout.activity_qq_step_view
    }

    override fun initView() {
        qqStepView = qq_step_view
    }

    override fun initListener() {

    }

    override fun initData() {
        qqStepView.stepMax = 2000
        Utils.intValueAnimator(0,1500,3000, ValueAnimator.AnimatorUpdateListener {
            val step = it.animatedValue as Int
            qqStepView.currentStep = step
        })
    }
}