package com.yxm.framelibrary.skin.attr

import android.view.View

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/8 13:55
 * @description
 */
class SkinAttr(val mResName:String,val mType:SkinType) {

    fun skin(view: View) {
        mType.skin(view,mResName)
    }
}