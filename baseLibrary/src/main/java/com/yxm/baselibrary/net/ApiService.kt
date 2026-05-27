package com.yxm.baselibrary.net

import com.yxm.baselibrary.base.BaseResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
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
    @POST
    @DELETE
    @PUT
    @Multipart
    fun getUser(@Path("login") name: String): Call<User>

    @GET("users/{login}")
    suspend fun getUser1(@Path("login") name: String): BaseResponse<User>

    fun getUserWithRxJava(@Path("login") name: String): Observable<User>

}