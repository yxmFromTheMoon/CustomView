package com.yxm.baselibrary.recyclerview

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/12 20:01
 * @description 多条目布局支持
 */
interface MultiTypeSupport<T> {
    fun getLayoutId(data:T): Int
}