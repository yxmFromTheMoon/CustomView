package com.yxm.customview.anr

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * 在主线程同步访问远程 ContentProvider，用于练习 Binder 阻塞导致的 ANR。
 */
class BinderBlockActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(Button(this).apply {
            text = "同步 Binder 调用阻塞 30 秒"
            setOnClickListener { queryRemoteProviderOnMainThread() }
        })
    }

    private fun queryRemoteProviderOnMainThread() {
        val uri = Uri.parse("content://$packageName.slow-provider/items")
        Log.d(TAG, "主线程开始同步 Binder 调用")
        contentResolver.query(uri, null, null, null, null)?.close()
        Log.d(TAG, "同步 Binder 调用结束")
    }

    private companion object {
        const val TAG = "ANR_TEST"
    }
}
