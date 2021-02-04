package com.yxm.baselibrary.startup

import android.app.Application
import android.content.Context
import com.yxm.baselibrary.Utils

/**
 * Created by yxm at 2021/2/4 16:42
 * @email: yxmbest@163.com
 * @description:
 */
object TaskDispatcher {

    private var sContext: Context? = null

    @Volatile
    private var mHasInit = false
    private var mIsMainProcess = true

    fun init(context: Context?) {
        if (context != null) {
            sContext = context
            mHasInit = true
            mIsMainProcess = Utils.isMainProcess(context)
        }
    }

    fun isMainProcess(): Boolean {
        return mIsMainProcess
    }

    fun getContext(): Context {
        return sContext!!
    }

    fun satisfyChildren(task: Task) {
    }

    fun markTaskDone(task: Task) {
    }
}