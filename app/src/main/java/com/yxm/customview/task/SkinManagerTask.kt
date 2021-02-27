package com.yxm.customview.task

import android.util.Log
import com.yxm.baselibrary.startup.MainTask
import com.yxm.baselibrary.startup.Task
import com.yxm.customview.basic.MyApplication
import com.yxm.framelibrary.skin.SkinManager

/**
 * Created by Myron at 2021/2/27 15:51
 * @email yxmbest@163.com
 * @description
 */
class SkinManagerTask : Task() {

    override fun run() {
        SkinManager.init(mContext)
        Log.d("test_task_run", "run main task")
    }

    override fun dependsOn(): List<Class<out Task?>?> {
        return mutableListOf(SkinPreUtilsTask::class.java, ImageLoaderTask::class.java)
    }
}