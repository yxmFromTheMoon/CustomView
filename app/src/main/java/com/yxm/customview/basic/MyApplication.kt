package com.yxm.customview.basic

import android.app.Application

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/27 13:17
 * @Description
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: MyApplication
    }
}