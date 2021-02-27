package com.yxm.customview

import android.util.Log

/**
 * Created by Myron at 2021/2/3 20:23
 * @email yxmbest@163.com
 * @description
 */
object LauncherTimer {
    private var sTime = 0L

    fun startRecord() {
        sTime = System.currentTimeMillis()
    }

    fun endRecord() {
        endRecord("onWindowFocusChanged")
    }

    fun endRecord(msg: String) {
        val cost = System.currentTimeMillis() - sTime
        Log.d("test_start_time", "${msg}:$cost")
    }
}