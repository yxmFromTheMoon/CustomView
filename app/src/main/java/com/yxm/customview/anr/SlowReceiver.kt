package com.yxm.customview.anr

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/** 故意在主线程阻塞广播处理，用于练习 BroadcastReceiver ANR。 */
class SlowReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "SlowReceiver 开始执行：${intent.action}")
        Thread.sleep(BLOCK_DURATION_MS)
        Log.d(TAG, "SlowReceiver 执行结束")
    }

    private companion object {
        const val TAG = "ANR_TEST"
        const val BLOCK_DURATION_MS = 30_000L
    }
}
