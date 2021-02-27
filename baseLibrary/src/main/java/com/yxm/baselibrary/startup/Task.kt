package com.yxm.baselibrary.startup

import android.os.Process
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * 抽象任务类
 */
abstract class Task : ITask {
    protected val TAG = javaClass.simpleName
    protected val mContext = TaskDispatcher.getContext()
    protected val mIsMainProcess = TaskDispatcher.isMainProcess()

    @Volatile
    var isWaiting = false // 是否正在等待

    @Volatile
    var isRunning = false // 是否正在执行

    @Volatile
    var isFinished = false// Task是否执行完成

    @Volatile
    var isSend = false// Task是否已经被分发

    // 当前Task依赖的Task数量（需要等待被依赖的Task执行完毕才能执行自己），默认没有依赖
    private val mDepends: CountDownLatch = CountDownLatch(if (dependsOn().isNullOrEmpty()) 0 else dependsOn()!!.size)

    /**
     * 等待所依赖的任务执行完毕
     */
    fun waitToSatisfy() {
        try {
            mDepends.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 执行一次依赖的任务
     */
    fun satisfy() {
        mDepends.countDown()
    }

    /**
     * 是否需要尽快执行，解决特殊场景的问题：一个Task耗时非常多但是优先级却一般，很有可能开始的时间较晚，
     * 导致最后只是在等它，这种可以早开始。
     *
     * @return
     */
    open fun needRunAsSoon(): Boolean {
        return false
    }

    override fun priority(): Int {
        return Process.THREAD_PRIORITY_BACKGROUND
    }

    /**
     * 异步线程执行的Task是否需要在被调用await的时候等待，默认不需要
     *
     * @return
     */
    override fun needWait(): Boolean {
        return false
    }

    /**
     * 当前Task依赖的Task集合（需要等待被依赖的Task执行完毕才能执行自己），默认没有依赖
     *
     * @return
     */
    override fun dependsOn(): List<Class<out Task?>?>? {
        return null
    }

    override fun runOnMainThread(): Boolean {
        return false
    }

    override fun onlyInMainProcess(): Boolean {
        return true
    }

    override fun runOn(): ExecutorService {
        return Executors.newCachedThreadPool()
    }

    override fun setTaskCallback(callback: TaskCallback) {

    }

    override fun getTailRunnable(): Runnable? {
        return null
    }

    override fun needCall(): Boolean {
        return false
    }
}
