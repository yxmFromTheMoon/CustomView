package com.yxm.baselibrary.base.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * Created by Myron at 2021/8/22 14:50
 * @email yxmbest@163.com
 * @description
 */
interface IPresenter<in V : BaseView> : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(view: V)

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(view: V)

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAnyLifeCycle()
}