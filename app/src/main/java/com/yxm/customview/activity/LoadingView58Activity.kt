package com.yxm.customview.activity

import com.yxm.customview.R
import com.yxm.customview.basic.BaseActivity
import com.yxm.customview.view.LoadingView58
import kotlinx.android.synthetic.main.activity_loading_view_58.*

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/27 13:05
 * @Description
 */
class LoadingView58Activity : BaseActivity() {

    private lateinit var loadingView: LoadingView58

    override fun getLayoutId(): Int {
        return R.layout.activity_loading_view_58
    }

    override fun initView() {
        loadingView = loading_view_58
        exchange()
    }

    override fun initListener() {

    }

    override fun initData() {
    }

    fun exchange() {
        Thread(Runnable {
            while (true) {
                runOnUiThread {
                    loadingView.exchange()
                }
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }).start()
    }
}