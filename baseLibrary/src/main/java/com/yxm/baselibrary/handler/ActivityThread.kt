package com.yxm.baselibrary.handler

/**
 * Created by Myron at 2020/11/21 14:55
 * @email yxmbest@163.com
 * @description
 */
class ActivityThread {
    private val mH: H = H()

    fun attach() {
        val activity = MainActivity()
        activity.onCreate()
    }

    class H : Handler() {
        override fun handleMessage(message: Message) {

        }
    }
}

fun main() {

    Looper.prepare()

    val activityThread = ActivityThread()
    activityThread.attach()

    Looper.loop()

}