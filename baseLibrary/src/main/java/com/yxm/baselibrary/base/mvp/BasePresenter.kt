package com.yxm.baselibrary.base.mvp

/**
 * Created by Myron at 2021/8/22 14:49
 * @email yxmbest@163.com
 * @description
 */
open class BasePresenter<V :BaseView> : IPresenter<V> {

    private var mView: V? = null

    fun attach(view: V) {
        mView = view
    }

    fun detach() {
        mView = null
    }

    fun getView(): V? {
        return mView
    }

    override fun onCreate(view: V) {
        mView = view
    }

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onDestroy(view: V) {
        mView = null
    }

    override fun onAnyLifeCycle() {
    }
}