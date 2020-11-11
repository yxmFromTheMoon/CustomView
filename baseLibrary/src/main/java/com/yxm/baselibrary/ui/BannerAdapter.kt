package com.yxm.baselibrary.ui

import android.view.View

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/11 8:03
 * @description
 */
abstract class BannerAdapter {

    abstract fun getView(position: Int): View
}