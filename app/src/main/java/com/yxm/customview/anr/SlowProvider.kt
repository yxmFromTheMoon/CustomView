package com.yxm.customview.anr

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log

/**
 * 运行在 :anr_remote 进程中，故意延迟响应同步 Binder 请求。
 */
class SlowProvider : ContentProvider() {

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        Log.d(TAG, "远程 Provider 开始处理请求")
        Thread.sleep(BLOCK_DURATION_MS)
        return MatrixCursor(arrayOf("_id", "name")).apply {
            addRow(arrayOf(1, "ANR practice result"))
        }
    }

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0

    private companion object {
        const val TAG = "ANR_TEST"
        const val BLOCK_DURATION_MS = 30_000L
    }
}
