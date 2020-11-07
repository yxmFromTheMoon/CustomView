package com.yxm.customview.activity

import android.animation.ValueAnimator
import com.yxm.customview.R
import com.yxm.baselibrary.base.BaseActivity
import com.yxm.customview.utils.Utils
import com.yxm.customview.view.CircleProgressView
import com.yxm.framelibrary.BaseSkinActivity
import kotlinx.android.synthetic.main.activity_circle_progress.*

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/27 13:57
 * @Description
 */
class CircleProgressViewActivity: BaseSkinActivity() {

    private lateinit var circleProgressView:CircleProgressView

    override fun getLayoutId(): Int {
        return R.layout.activity_circle_progress
    }

    override fun initView() {
        circleProgressView = circle_progress_view
    }

    override fun initListener() {
    }

    override fun initData() {
    }

    override fun onResume() {
        super.onResume()
        Utils.intValueAnimator(1,100,3000, ValueAnimator.AnimatorUpdateListener {
            val value = it.animatedValue as Int
            circleProgressView.setCurrentValue(value)
        })
    }
}