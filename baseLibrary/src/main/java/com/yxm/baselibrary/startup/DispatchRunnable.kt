package com.yxm.baselibrary.startup

import android.os.Process
import android.util.Log
import androidx.core.os.TraceCompat

/**
 * Created by yxm at 2021/2/4 17:11
 * @email: yxmbest@163.com
 * @description: task真正执行的地方
 */
class DispatchRunnable @JvmOverloads constructor(private val task: Task,
                                                 private val taskDispatcher: TaskDispatcher? = null) : Runnable {

    override fun run() {
        TraceCompat.beginSection(task.javaClass.simpleName)
        Process.setThreadPriority(task.priority())
        var startTime = System.currentTimeMillis()
        task.isWaiting = true
        task.waitToSatisfy()
        val waitTime = System.currentTimeMillis() - startTime
        startTime = System.currentTimeMillis()
        Log.d("test_dispatch", "waitTime:$waitTime")
        task.isRunning = true
        task.run()
        val runTime = System.currentTimeMillis() - startTime
        Log.d("test_dispatch", "runTime:$runTime")
        startTime = System.currentTimeMillis()
        /**
         * 执行task尾部任务
         */
        task.getTailRunnable()?.run()

        if (!task.needCall() || !task.runOnMainThread()) {
            //TaskStat.markTaskDone()
            task.isFinished = true
            if (taskDispatcher != null) {
                taskDispatcher.satisfyChildren(task)
                taskDispatcher.markTaskDone(task)
            }
        }
        TraceCompat.endSection()
    }
}