package com.yxm.baselibrary.base.mvp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Myron at 2021/8/22 14:44
 * @email yxmbest@163.com
 * @description base mvp activity
 */
abstract class BaseMvpActivity<P : BasePresenter<*>> : AppCompatActivity(), BaseView {

    private var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mPresenter = createPresenter()
        //mPresenter?.attach(this)
        //lifecycle.addObserver(mPresenter!!)

        initView()
        initData()
    }

    abstract fun initData()

    abstract fun initView()

    abstract fun getLayoutId(): Int

    protected fun getPresenter(): P? {
        return mPresenter
    }

    abstract fun createPresenter(): P

    override fun showLoading() {
        Log.d("test_loading", "loading")
    }

    override fun hideLoading() {
        Log.d("test_loading", "hide loading")
    }
}