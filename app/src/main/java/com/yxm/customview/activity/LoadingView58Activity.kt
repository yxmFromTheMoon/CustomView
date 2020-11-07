package com.yxm.customview.activity

import com.yxm.customview.R
import com.yxm.baselibrary.base.BaseActivity
import com.yxm.customview.view.LoadingView
import com.yxm.framelibrary.BaseSkinActivity
import kotlinx.android.synthetic.main.activity_loading_view_58.*

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/27 13:05
 * @Description
 */
class LoadingView58Activity : BaseSkinActivity() {

    private lateinit var loadingView: LoadingView

    override fun getLayoutId(): Int {
        return R.layout.activity_loading_view_58
    }

    override fun initView() {
        loadingView = loading_view
    }

    override fun initListener() {

    }

    override fun initData() {
    }

}