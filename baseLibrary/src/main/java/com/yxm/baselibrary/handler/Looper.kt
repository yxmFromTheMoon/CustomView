package com.yxm.baselibrary.handler

/**
 * Created by Myron at 2020/11/21 14:50
 * @email yxmbest@163.com
 * @description
 */
class Looper(quitAllowed: Boolean) {

    var mQueue: MessageQueue = MessageQueue(quitAllowed)

    companion object {

        private val sThreadLocal = ThreadLocal<Looper>()

        fun prepare() {
            prepare(true);
        }

        fun myLooper(): Looper? {
            return sThreadLocal.get()
        }

        fun loop() {
            val me = myLooper()
                    ?: throw java.lang.RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.")
            val queue: MessageQueue = me.mQueue

            while (true) {
                val msg: Message = queue.next() ?: return
                msg.target?.dispatchMessage(msg)
            }
        }

        private fun prepare(quitAllowed: Boolean) {
            if (sThreadLocal.get() != null) {
                throw RuntimeException("Only one Looper may be created per thread")
            }
            sThreadLocal.set(Looper(quitAllowed))
        }
    }
}