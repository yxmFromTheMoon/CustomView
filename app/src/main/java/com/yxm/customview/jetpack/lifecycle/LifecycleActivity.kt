package com.yxm.customview.jetpack.lifecycle

import com.yxm.baselibrary.base.BaseActivity
import com.yxm.customview.R

/**
 * Created by Myron at 2021/1/7 20:17
 * @email yxmbest@163.com
 * @description
 */
class LifecycleActivity:BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }

    override fun initView() {
        lifecycle.addObserver(MyLocationListener())
    }

    override fun initListener() {
    }

    override fun initData() {
    }
}