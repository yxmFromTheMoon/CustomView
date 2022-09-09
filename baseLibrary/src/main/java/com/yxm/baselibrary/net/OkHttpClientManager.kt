package com.yxm.baselibrary.net

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/21 20:13
 * @description
 */
object OkHttpClientManager {

    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30L, TimeUnit.SECONDS)
            .callTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .addInterceptor(BaseUrlInterceptor(mutableSetOf("https://api.example.com")))
            .build()
    }
}