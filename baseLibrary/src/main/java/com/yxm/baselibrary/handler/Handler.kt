package com.yxm.baselibrary.handler


/**
 * Created by Myron at 2020/11/21 14:49
 * @email yxmbest@163.com
 * @description
 */
open class Handler {
    private var mLooper: Looper? = Looper.myLooper()
    private var mQueue: MessageQueue? = mLooper?.mQueue

    fun sendMessage(message: Message) {
        sendMessageDelayed(message, 0)
    }

    open fun handleMessage(message: Message) {}

    fun sendMessageDelayed(msg: Message, delayMillis: Long): Boolean {
        var delayMillisTemp = delayMillis

        if (delayMillisTemp < 0) {
            delayMillisTemp = 0
        }
        return sendMessageAtTime(msg, System.currentTimeMillis() + delayMillis)
    }

    fun sendMessageAtTime(msg: Message, uptimeMillis: Long): Boolean {
        val queue: MessageQueue? = mQueue
        if (queue == null) {
            val e = RuntimeException("$this sendMessageAtTime() called with no mQueue")
            return false
        }
        return enqueueMessage(queue, msg, uptimeMillis)
    }

    private fun enqueueMessage(queue: MessageQueue, msg: Message,
                               uptimeMillis: Long): Boolean {
        msg.target = this
        return queue.enqueueMessage(msg, uptimeMillis)
    }

    fun dispatchMessage(msg: Message) {
        handleMessage(msg)
    }
}