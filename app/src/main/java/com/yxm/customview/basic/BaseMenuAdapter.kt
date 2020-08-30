package com.yxm.customview.basic

import android.view.View
import android.view.ViewGroup

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/8/30 8:58
 * @description
 */
abstract class BaseMenuAdapter {

    //获取数量
    abstract fun getCount(): Int

    //获取tab
    abstract fun getTabView(position: Int, parent: ViewGroup): View

    //获取内容
    abstract fun getMenuView(position: Int, parent: ViewGroup): View

    abstract fun onMenuOpen(tabView:View)

    abstract fun onMenuClose(tabView:View)
}