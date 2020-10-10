package com.yxm.baselibrary.ioc


/**
 *@author: yxm
 *@time: 2020/10/10
 *@description: View Click事件注解的Annotation
 */
// @Target(AnnotationTarget.FIELD) 代表Annotation的位置  FIELD属性  TYPE类上  CONSTRUCTOR 构造函数上
@Target(AnnotationTarget.FUNCTION)
// @Retention(AnnotationTarget.CLASS) 什么时候生效 CLASS编译时
// RUNTIME运行时  SOURCE源码资源
@Retention(AnnotationRetention.RUNTIME)
annotation class OnClick( // --> @ViewById(R.id.xxx)
        vararg val value: Int)