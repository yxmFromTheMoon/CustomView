package com.yxm.customview.jetpack.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * Created by Myron at 2021/1/7 20:14
 * @email yxmbest@163.com
 * @description
 */
class MyLocationListener:LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun startLocation(){
        println("开始定位")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pauseLocation(){
        println("暂停定位")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stopLocation(){
        println("停止定位")
    }
}