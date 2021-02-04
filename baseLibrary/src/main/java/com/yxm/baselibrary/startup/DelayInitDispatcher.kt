package com.yxm.baselibrary.startup

import android.os.Looper
import android.os.MessageQueue
import java.util.*

/**
 * Created by yxm at 2021/2/4 18:07
 * @email: yxmbest@163.com
 * @description:延迟执行初始化task,idleHandler会在系统空闲的时候执行，messageQueue为空时
 */
class DelayInitDispatcher {

    private val mDelayInitTask = LinkedList<Task>()

    private val mDelayInitIdleHandler = MessageQueue.IdleHandler {
        if (mDelayInitTask.isNotEmpty()) {
            val task = mDelayInitTask.pop()
            DispatchRunnable(task).run()
        }
        return@IdleHandler !mDelayInitTask.isEmpty()
    }

    fun addTask(task: Task): DelayInitDispatcher {
        mDelayInitTask.add(task)
        return this
    }

    fun start() {
        Looper.myQueue().addIdleHandler(mDelayInitIdleHandler)
    }

}