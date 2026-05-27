package com.yxm.baselibrary.net

import com.yxm.baselibrary.base.BaseResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/21 20:46
 * @description
 */
object HttpUtils {

    private val mService = RetrofitManager.create<ApiService>()

    suspend fun getUser(name: String) = mService.getUser(name).await()

    suspend fun getUser1(name: String): BaseResponse<User> = mService.getUser1(name)

    private fun getUserWithRxJava(name: String) = mService.getUserWithRxJava(name)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            it.name
        }

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(RuntimeException("数据为空"))
                    }
                }
            })
        }
    }
}