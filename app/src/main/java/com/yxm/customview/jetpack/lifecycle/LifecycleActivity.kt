package com.yxm.customview.jetpack.lifecycle

import androidx.lifecycle.lifecycleScope
import com.yxm.baselibrary.base.BaseActivity
import com.yxm.customview.R
import com.yxm.customview.basic.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * Created by Myron at 2021/1/7 20:17
 * @email yxmbest@163.com
 * @description
 */
class LifecycleActivity : BaseActivity() {
    lateinit var testInit: TextView

    private val testLazy by lazy {

    }

    companion object{

        @JvmStatic
        fun test(){

        }

        fun testNotStatic(){

        }
    }

    override fun getLayoutId(): Int {
        ::testInit.isInitialized
        return R.layout.activity_test
    }

    override fun initView() {
        lifecycleScope.launch {
            loadData()
        }

    }

    override fun initListener() {
    }

    override fun initData() {
    }

    private suspend fun loadData(): MutableList<String> {
        delay(2000)
        return mutableListOf()
    }
}