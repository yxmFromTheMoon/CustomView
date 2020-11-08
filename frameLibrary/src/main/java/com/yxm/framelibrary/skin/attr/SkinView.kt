package com.yxm.framelibrary.skin.attr

import android.view.View

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/8 13:54
 * @description
 */
class SkinView(val view:View,val attrs:List<SkinAttr>) {

    private lateinit var mView:View
    private lateinit var mAttrs:List<SkinAttr>

    fun skin(){
        mAttrs.forEach {
            it.skin(mView)
        }
    }
}