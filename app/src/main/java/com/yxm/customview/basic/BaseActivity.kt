package com.yxm.customview.basic

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/26 22:49
 * @Description
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this;
        if (getLayoutId() != 0) {
            setContentView(getLayoutId())
        }
        initView()
        initData()
        initListener()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun initListener()

    abstract fun initData()

    companion object {
        lateinit var mContext: Context
    }
}