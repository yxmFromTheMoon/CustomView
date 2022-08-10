package com.yxm.baselibrary.net

import android.util.Log
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/26 20:17
 * @description
 */
class Repository {

    fun getUserName(name: String) = fire(Dispatchers.IO) {
        val user = HttpUtils.getUser(name)
        Result.success(user)
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Log.d("test", e.message ?: "")
                Result.failure(e)
            }
            emit(result)
        }
}