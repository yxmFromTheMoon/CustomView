package com.yxm.customview.activity

import android.annotation.SuppressLint
import com.yxm.customview.R
import com.yxm.customview.databinding.ActivityTestBinding
import com.yxm.customview.proxy.Proxy
import com.yxm.framelibrary.BaseSkinActivity

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/21 20:33
 * @description
 */
class TestActivity : BaseSkinActivity() {

    private val binding by lazy {
        ActivityTestBinding.inflate(layoutInflater)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        Proxy().proxy()
        binding.btnTest.setOnClickListener {

        }
    }

    override fun initListener() {

    }

    override fun initData() {

    }

}