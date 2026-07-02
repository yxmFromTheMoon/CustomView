package com.yxm.baselibrary.base.mvp

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Created by Myron at 2021/8/22 14:49
 * @email yxmbest@163.com
 * @description
 */
open class BasePresenter<V : BaseView> : IPresenter<V>, DefaultLifecycleObserver {

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

    override fun onCreate(owner: LifecycleOwner) {
        onAnyLifeCycle()
        mView?.let { onCreate(it) }
    }

    override fun onStart(owner: LifecycleOwner) {
        onAnyLifeCycle()
        onStart()
    }

    override fun onResume(owner: LifecycleOwner) {
        onAnyLifeCycle()
        onResume()
    }

    override fun onPause(owner: LifecycleOwner) {
        onAnyLifeCycle()
        onPause()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        onAnyLifeCycle()
        mView?.let { onDestroy(it) }
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
