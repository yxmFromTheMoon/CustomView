package com.yxm.baselibrary.ioc

/**
 *@author: yxm
 *@time: 2020/10/10
 *@description: finViewById注解
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewById(
        val value: Int
)