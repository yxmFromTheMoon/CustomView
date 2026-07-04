package com.yxm.customview.anr

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * 故意阻塞主线程，用于练习分析 Input dispatching timed out 类型的 ANR。
 * 仅限调试使用。
 */
class MainThreadBlockActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(Button(this).apply {
            text = "主线程阻塞 10 秒"
            setOnClickListener {
                Log.d(TAG, "开始阻塞主线程")
                Thread.sleep(BLOCK_DURATION_MS)
                Log.d(TAG, "主线程阻塞结束")
            }
        })
    }

    private companion object {
        const val TAG = "ANR_TEST"
        const val BLOCK_DURATION_MS = 10_000L
    }
}
