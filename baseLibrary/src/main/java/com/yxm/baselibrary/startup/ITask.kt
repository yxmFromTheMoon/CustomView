package com.yxm.baselibrary.startup

import android.os.Process
import android.util.Log
import androidx.annotation.IntRange
import java.util.concurrent.Executor

/**
 * Created by yxm at 2021/2/4 16:22
 * @email: yxmbest@163.com
 * @description:
 */
interface ITask {

    /**
     * task的优先级
     */
    @IntRange(from = Process.THREAD_PRIORITY_FOREGROUND.toLong(), to = Process.THREAD_PRIORITY_LOWEST.toLong())
    fun priority(): Int

    /**
     * task依赖的task,需在依赖的task执行完后才会执行
     */
    fun dependsOn(): List<Class<out Task?>?>?

    /**
     * 是否需要等待执行，默认不需要
     */
    fun needWait(): Boolean

    fun needCall(): Boolean

    /**
     * 所有任务执行完后要执行的其他任务
     */
    fun getTailRunnable(): Runnable?

    fun setTaskCallback(callback: TaskCallback)

    /**
     * 在主线程执行
     */
    fun runOnMainThread(): Boolean

    /**
     * 执行的线程池
     */
    fun runOn(): Executor

    /**
     * 只在主进程执行
     */
    fun onlyInMainProcess(): Boolean

    /**
     * 真正做事的方法
     */
    fun run()
}