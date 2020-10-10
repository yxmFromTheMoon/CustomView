package com.yxm.customview.activity

import androidx.recyclerview.widget.RecyclerView
import com.yxm.customview.R
import com.yxm.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_common_adapter.*

/**
 *@author yxm
 *@time 2020/9/16 3:56 PM
 *@description 万能adapter测试
 */
class CommonAdapterActivity: BaseActivity() {

    private lateinit var mRv:RecyclerView


    override fun getLayoutId(): Int {
        return R.layout.activity_common_adapter
    }

    override fun initView() {
        mRv = test_rv
    }

    override fun initListener() {

    }

    override fun initData() {
    }
}