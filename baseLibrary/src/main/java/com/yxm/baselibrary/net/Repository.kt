package com.yxm.baselibrary.net

import android.util.Log
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/26 20:17
 * @description
 */
object Repository {

    fun getUserName(name: String) = liveData<Result<User>>(Dispatchers.Main) {
        val result = try {
            val user = HttpUtils.getUser(name)
            Result.success(user)
        } catch (e: Exception) {
            Log.d("Test1", e.message)
            Result.failure(RuntimeException("response is empty"))
        }
        emit(result)
    }
}