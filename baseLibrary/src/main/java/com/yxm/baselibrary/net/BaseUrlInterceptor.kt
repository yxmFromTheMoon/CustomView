package com.yxm.baselibrary.net

import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import java.lang.reflect.Method

/**
 * Created by yxm on 2022/9/8 5:52 下午
 * @email: yinxiangming@lightinthebox.com
 * @description:
 */
class BaseUrlInterceptor(private val baseUrl: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val invocation = request.tag(Invocation::class.java)
        val method = invocation?.method()
        method?.getIgnoreAnnotationOrNull()?.let {
            return chain.proceed(chain.request())
        }
        var baseUrlAnnotation = method?.annotations?.firstOrNull() as? BaseUrl
        if (baseUrlAnnotation == null) {
            baseUrlAnnotation = method?.declaringClass?.annotations?.firstOrNull() as? BaseUrl
        }
        baseUrlAnnotation ?: return chain.proceed(request)
        val oldUrl = request.url().toString()
        val newUrl = if (oldUrl.startsWith(baseUrl)) {
            baseUrlAnnotation.value + oldUrl.replaceFirst(baseUrl, "")
        } else {
            oldUrl
        }
        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }

    private fun Method.getIgnoreAnnotationOrNull(): Ignore? = getAnnotation(Ignore::class.java)
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
annotation class BaseUrl(val value: String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
annotation class Ignore