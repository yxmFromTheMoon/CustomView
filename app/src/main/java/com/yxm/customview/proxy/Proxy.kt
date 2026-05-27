package com.yxm.customview.proxy

import android.util.Log
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

/**
 * Created by yxm on 2022/11/4 11:20 上午
 * @email: yinxiangming@lightinthebox.com
 * @description:
 */
class Proxy {

    fun proxy() {
        val invocationHandler = InvocationHandler { proxy, method, args ->
            Log.d("Proxy", "${proxy::class.java.name},${method.name},$args")
            null
        }
        val loader = ProxyInterface::class.java.classLoader
        val interfaces = arrayOf(ProxyInterface::class.java)
        val proxy = create(ProxyInterface::class.java)
        proxy?.proxy()
        proxy?.proxy1()
        proxy?.proxy2()
        proxy?.proxy3()
    }

    fun <T> create(clazz: Class<T>): T {
        return Proxy.newProxyInstance(
            clazz.classLoader, arrayOf(clazz)
        ) { proxy, method, args ->
            //method.invoke(proxy, args)
            Log.d("Proxy", "${proxy::class.java.name},${method.name},$args")
            //return@newProxyInstance method.invoke(this, args)
            return@newProxyInstance proxy
        } as T
    }
}

abstract class ServiceMethod<T> {
    abstract fun invoke(): T
}