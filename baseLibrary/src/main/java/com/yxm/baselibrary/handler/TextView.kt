package com.yxm.baselibrary.handler

import android.util.Log
import java.lang.RuntimeException

/**
 * Created by Myron at 2020/11/21 14:51
 * @email yxmbest@163.com
 * @description
 */
class TextView {
    private val mCurrentThread: Thread = Thread.currentThread()

    fun setText(text: CharSequence) {
        checkThread()
    }

    private fun checkThread() {
        if (mCurrentThread != Thread.currentThread()) {
            throw RuntimeException("不能在子线程中更新UI")
        }
    }
}