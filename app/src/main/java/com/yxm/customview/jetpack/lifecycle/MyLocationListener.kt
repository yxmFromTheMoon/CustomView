package com.yxm.customview.jetpack.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Created by Myron at 2021/1/7 20:14
 * @email yxmbest@163.com
 * @description
 */
class MyLocationListener : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        println("开始定位")
    }

    override fun onPause(owner: LifecycleOwner) {
        println("暂停定位")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        println("停止定位")
    }
}

