package com.yxm.customview.anr

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * 主线程持锁并等待子线程，子线程又等待同一把锁，形成死锁。
 * 仅限调试使用。
 */
class DeadLockActivity : AppCompatActivity() {

    private val lock = Any()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(Button(this).apply {
            text = "制造主线程死锁"
            setOnClickListener { createDeadLock() }
        })
    }

    private fun createDeadLock() {
        synchronized(lock) {
            val worker = Thread({
                Log.d(TAG, "子线程等待获取锁")
                synchronized(lock) {
                    Log.d(TAG, "子线程获取到锁")
                }
            }, "anr-worker")

            worker.start()
            worker.join()
        }
    }

    private companion object {
        const val TAG = "ANR_TEST"
    }
}
