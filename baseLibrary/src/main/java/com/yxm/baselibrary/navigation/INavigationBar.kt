package com.yxm.baselibrary.navigation

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/17 13:31
 * @description 头部导航栏规范
 */
interface INavigationBar {
    /**
     * 头部viewIds
     */
    fun bindLayoutId(): Int

    /**
     * 绑定头部的参数
     */
    fun applyView()
}