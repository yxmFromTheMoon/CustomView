package com.yxm.framelibrary

import android.content.Context
import android.util.Log

/**
 *@author: yxm
 *@time: 2020/10/10
 *@description: 全局异常处理
 */
object CrashExceptionHandler : Thread.UncaughtExceptionHandler {

    private lateinit var mContext: Context
    private const val TAG = "CrashExceptionHandler"

    override fun uncaughtException(t: Thread, e: Throwable) {
        //可将异常上传服务器，以文件形式发送等处理
        //除了异常之外，还需要保存app，用户手机的一些信息，如当前版本号，机型，sdk版本等等
        Log.e(TAG, "捕捉到异常")
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    fun init(context: Context) {
        this.mContext = context
    }
}