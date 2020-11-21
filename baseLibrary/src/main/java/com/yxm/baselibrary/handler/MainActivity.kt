package com.yxm.baselibrary.handler

import android.util.Log

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
    }

    override fun onResume() {
        Log.d("test_handler", "onResume()")
    }
}