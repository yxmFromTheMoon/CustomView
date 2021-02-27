package com.yxm.customview.task

import android.util.Log
import com.yxm.baselibrary.startup.Task
import com.yxm.customview.basic.MyApplication
import com.yxm.framelibrary.CrashExceptionHandler

/**
 * Created by Myron at 2021/2/27 15:47
 * @email yxmbest@163.com
 * @description
 */
class CrashExceptionHandlerTask : Task() {

    override fun run() {
        Log.d("test_task_run","run task")
        CrashExceptionHandler.init(mContext)
    }
}