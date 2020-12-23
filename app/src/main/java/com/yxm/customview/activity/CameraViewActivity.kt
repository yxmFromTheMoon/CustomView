package com.yxm.customview.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import com.yxm.baselibrary.base.BaseActivity
import com.yxm.customview.R
import kotlinx.android.synthetic.main.activity_test.*

/**
 * Created by yxm at 2020/12/23 18:38
 * @email: yxmbest@163.com
 * @description:
 */
class CameraViewActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_camera_view
    }

    override fun initView() {
        val animator1 = ObjectAnimator.ofFloat(camera_view, "flipRotation", 360f)
        val animator2 = ObjectAnimator.ofFloat(camera_view, "flipTop", 60f)
        val animator3 = ObjectAnimator.ofFloat(camera_view, "flipBottom", 60f)
        val animator4 = ObjectAnimator.ofFloat(camera_view, "flipBottom", 0f)
        val animator5 = ObjectAnimator.ofFloat(camera_view, "flipTop", 0f)

        val set = AnimatorSet()
        set.playSequentially(animator3, animator1, animator2, animator4, animator5)
        set.duration = 3000
        set.startDelay = 1000
        set.start()
    }

    override fun initListener() {
    }

    override fun initData() {
    }
}