package com.yxm.baselibrary.net

import com.yxm.baselibrary.base.BaseResponse
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/31 12:41
 * @description
 */
abstract class RetrofitCallback<T> : Callback<BaseResponse<T>> {

    companion object {
        private const val SUCCESS_CODE = 200
        private const val NETWORK_ERROR = -1
        private const val IO_ERROR = -2
        private const val OTHER_ERROR = -3
        private const val JSON_ERROR = -4
    }

    override fun onFailure(call: Call<BaseResponse<T>>, t: Throwable) {
        when (t) {
            is SocketTimeoutException -> {
                onFailure("网络连接超时", NETWORK_ERROR)
            }
            is UnknownHostException -> {
                onFailure("未找到主机，请检查IP地址是否正确", NETWORK_ERROR)
            }
            is ConnectException -> {
                onFailure("请检查网络连接", NETWORK_ERROR)
            }
            is IOException -> {
                onFailure("读写异常", IO_ERROR)
            }
            is JSONException -> {
                onFailure("json转换异常", JSON_ERROR)
            }
            else -> {
                onFailure(t.message!!, OTHER_ERROR)
            }
        }
    }

    override fun onResponse(call: Call<BaseResponse<T>>, response: Response<BaseResponse<T>>) {
        if (response.isSuccessful) {
            val result = response.body()
            if (result?.data != null) {
                onSuccess(result.data)
            } else {
                onFailure("请求成功，但是数据为空", SUCCESS_CODE)
            }
        }
    }

    abstract fun onSuccess(result: T?)

    abstract fun onFailure(msg: String, code: Int)
}