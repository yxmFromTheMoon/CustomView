package com.yxm.baselibrary.net

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/21 20:09
 * @description
 */
object RetrofitManager {

    private val mRetrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .client(OkHttpClientManager.getOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = mRetrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}