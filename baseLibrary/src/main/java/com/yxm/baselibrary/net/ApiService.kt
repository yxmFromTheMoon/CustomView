package com.yxm.baselibrary.net

import com.yxm.baselibrary.base.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/21 20:19
 * @description
 */
@Ignore
interface ApiService {

    @BaseUrl("https://api.example.com")
    @GET("users/{login}")
    fun getUser(@Path("login") name: String): Call<User>

    @GET("users/{login}")
    fun getUser1(@Path("login") name: String): Call<BaseResponse<User>>

}