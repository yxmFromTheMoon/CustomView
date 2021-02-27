package com.yxm.baselibrary.startup

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.annotation.UiThread
import com.yxm.baselibrary.Utils
import com.yxm.baselibrary.startup.sort.TaskSortUtil
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by yxm at 2021/2/4 16:42
 * @email: yxmbest@163.com
 * @description:
 */
@SuppressLint("StaticFieldLeak")
object TaskDispatcher {

    private var sContext: Context? = null
    private var mStartTime = 0L
    private const val WAIT_TIME = 10000L
    private val mFutures: MutableList<Future<*>> = ArrayList()

    private var mAllTasks: MutableList<Task> = ArrayList()
    private val mClsAllTasks: MutableList<Class<out Task>> = ArrayList()

    @Volatile
    private var mMainThreadTasks: MutableList<Task> = ArrayList()
    private var mCountDownLatch: CountDownLatch? = null
    private val mNeedWaitCount: AtomicInteger = AtomicInteger() //保存需要Wait的Task的数量

    private val mNeedWaitTasks: MutableList<Task> = ArrayList() //调用了await的时候还没结束的且需要等待的Task

    @Volatile
    private var mFinishedTasks: MutableList<Class<out Task>> = ArrayList(100) //已经结束了的Task

    private val mDependedHashMap: HashMap<Class<out Task>, MutableList<Task>> = HashMap()
    private val mAnalyseCount: AtomicInteger = AtomicInteger() //启动器分析的次数，统计下分析的耗时；

    @Volatile
    private var mHasInit = false
    private var mIsMainProcess = true

    fun init(context: Context?) {
        if (context != null) {
            sContext = context.applicationContext
            mHasInit = true
            mIsMainProcess = Utils.isMainProcess(context)
        }
    }

    fun createInstance(): TaskDispatcher {
        if (mHasInit.not()) {
            throw RuntimeException("must call init()")
        }
        return TaskDispatcher
    }

    fun addTask(task: Task?): TaskDispatcher {
        if (task != null) {
            collectDepends(task)
            mAllTasks.add(task)
            mClsAllTasks.add(task.javaClass)
            // 非主线程且需要wait的，主线程不需要CountDownLatch也是同步的
            if (ifNeedWait(task)) {
                mNeedWaitTasks.add(task)
                mNeedWaitCount.getAndIncrement()
            }
        }
        return this
    }

    private fun collectDepends(task: Task) {
        if (!task.dependsOn().isNullOrEmpty()) {
            task.dependsOn()!!.forEach {
                if (mDependedHashMap[it] == null) {
                    mDependedHashMap[it!!] = mutableListOf()
                }
                mDependedHashMap[it]?.add(task)
                if (mFinishedTasks.contains(it)) {
                    task.satisfy()
                }
            }
        }
    }

    private fun ifNeedWait(task: Task): Boolean {
        return !task.runOnMainThread() && task.needWait()
    }

    @UiThread
    fun start() {
        mStartTime = System.currentTimeMillis()
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw RuntimeException("must called from main thread")
        }
        if (mAllTasks.isNotEmpty()) {
            mAnalyseCount.getAndIncrement()
            printDependedMsg()
            mAllTasks = TaskSortUtil.getSortResult(mAllTasks, mClsAllTasks)
            mCountDownLatch = CountDownLatch(mNeedWaitCount.get())

            sendAndExecuteAsyncTask()

            executeTaskMain()
        }
    }

    /**
     * 查看被依赖的信息
     */
    private fun printDependedMsg() {
        for (cls in mDependedHashMap.keys) {
            for (task in mDependedHashMap[cls]!!) {
                Log.d("depend cls", task.javaClass.simpleName)
            }
        }
    }

    private fun sendAndExecuteAsyncTask() {
        mAllTasks.forEach {
            if (it.onlyInMainProcess() && !mIsMainProcess) {
                markTaskDone(it)
            } else {
                sendTaskReal(it)
            }
            it.isSend = true
        }
    }

    private fun sendTaskReal(task: Task) {
        if (task.runOnMainThread()) {
            mMainThreadTasks.add(task)
            if (task.needCall()) {
                task.setTaskCallback(object : TaskCallback {
                    override fun call() {
                        TaskStat.markTaskDone()
                        task.isFinished = true
                        satisfyChildren(task)
                        markTaskDone(task)
                        Log.i("testLog", "call");
                    }
                })
            }
        } else {
            // 直接发，是否执行取决于具体线程池
            val future: Future<*> = task.runOn().submit(DispatchRunnable(task, this))
            mFutures.add(future)
        }
    }

    private fun executeTaskMain() {
        mStartTime = System.currentTimeMillis()
        mMainThreadTasks.forEach {
            val time = System.currentTimeMillis()
            DispatchRunnable(it, this).run()
        }
    }

    fun executeTask(task: Task) {
        if (ifNeedWait(task)) {
            mNeedWaitCount.getAndIncrement()
        }
        task.runOn().execute(DispatchRunnable(task, this))
    }

    @UiThread
    fun await() {
        try {
            if (mNeedWaitCount.get() > 0) {
                mCountDownLatch!!.await(WAIT_TIME, TimeUnit.MILLISECONDS)
            }
        } catch (e: InterruptedException) {
        }
    }

    fun cancel() {
        mFutures.forEach {
            it.cancel(true)
        }
    }

    fun isMainProcess(): Boolean {
        return mIsMainProcess
    }

    fun getContext(): Context {
        return sContext!!
    }

    /**
     * 通知Children一个前置任务已完成
     *
     * @param task
     */
    fun satisfyChildren(task: Task) {
        val arrayList: MutableList<Task>? = mDependedHashMap[task.javaClass]
        if (arrayList != null && arrayList.size > 0) {
            for (task in arrayList) {
                task.satisfy()
            }
        }
    }

    fun markTaskDone(task: Task) {
        if (ifNeedWait(task)) {
            mFinishedTasks.add(task.javaClass)
            mNeedWaitTasks.remove(task)
            mCountDownLatch?.countDown()
            mNeedWaitCount.getAndDecrement()
        }
    }

}