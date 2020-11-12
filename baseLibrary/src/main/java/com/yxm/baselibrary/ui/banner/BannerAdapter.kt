package com.yxm.baselibrary.ui.banner

import android.view.View

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/11 8:03
 * @description
 */
abstract class BannerAdapter {

    abstract fun getView(position: Int,convertView: View?): View

    abstract fun getCount(): Int

    open fun getBannerDesc(position: Int): String{
        return ""
    }
}