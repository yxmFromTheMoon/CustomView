package com.yxm.baselibrary.handler

import android.util.Log
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by Myron at 2020/11/21 14:55
 * @email yxmbest@163.com
 * @description
 */
class MainActivity : Activity() {
    private var mTextView: TextView? = null

    private var mHandler = object : Handler() {

        override fun handleMessage(message: Message) {
            val obj = message.obj as String
            mTextView?.setText(obj)
            println(obj)

        }
    }

    override fun onCreate() {
        mTextView = TextView()

        Thread {
            Thread.sleep(1000)
            //mTextView?.setText("更新UI")
            val message = Message()
            message.obj = "更新UI"
            mHandler.sendMessage(message)
        }.start()

        EventBus.getDefault().register(this)
        EventBus.getDefault().unregister(this)
        EventBus.getDefault().post(100)
    }

    override fun onResume() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100, sticky = true)
    fun test1(text: String) {
        mTextView?.setText(text)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100, sticky = true)
    fun test2(text: String) {
        mTextView?.setText(text)
    }


    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100, sticky = true)
    fun test3(int: Int) {
        mTextView?.setText(int.toString())
    }
}