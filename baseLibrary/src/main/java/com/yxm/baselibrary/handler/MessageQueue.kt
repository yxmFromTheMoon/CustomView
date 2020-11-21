package com.yxm.baselibrary.handler

/**
 * Created by Myron at 2020/11/21 14:50
 * @email yxmbest@163.com
 * @description
 */
class MessageQueue(quitAllowed: Boolean) {

    private var mMessages: Message? = null

    fun enqueueMessage(msg: Message, `when`: Long): Boolean {
        synchronized(this) {
            msg.`when` = `when`
            var p: Message? = mMessages
            if (p == null || `when` == 0L || `when` < p.`when`) {
                // New head, wake up the event queue if blocked.
                msg.next = p
                mMessages = msg
            } else {
                // Inserted within the middle of the queue.  Usually we don't have to wake
                // up the event queue unless there is a barrier at the head of the queue
                // and the message is the earliest asynchronous message in the queue.
                var prev: Message?
                while (true) {
                    prev = p
                    p = p?.next
                    if (p == null || `when` < p.`when`) {
                        break
                    }
                }
                msg.next = p // invariant: p == prev.next
                prev?.next = msg
            }
        }
        return true
    }

    fun next(): Message? {
        while (true) {
            synchronized(this) {
                // Try to retrieve the next message.  Return if found.
                val now = System.currentTimeMillis()
                var prevMsg: Message? = null
                var msg: Message? = mMessages
                if (msg != null && msg.target == null) {
                    // Stalled by a barrier.  Find the next asynchronous message in the queue.
                    do {
                        prevMsg = msg
                        msg = msg?.next
                    } while (msg != null)
                }
                if (msg != null) {
                    if (now < msg.`when`) {
                    } else {
                        if (prevMsg != null) {
                            prevMsg.next = msg.next
                        } else {
                            mMessages = msg.next
                        }
                        msg.next = null
                        return msg
                    }
                }
            }
        }
    }


}