package com.yxm.baselibrary.net

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/21 20:19
 * @description
 */
interface ApiService {
    @GET("users/{login}")
    suspend fun getUser(@Path("login") name: String): User
}