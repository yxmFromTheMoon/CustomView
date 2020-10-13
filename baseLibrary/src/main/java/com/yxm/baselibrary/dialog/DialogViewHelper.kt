package com.yxm.baselibrary.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/13 20:43
 * @description
 */
class DialogViewHelper() {

    var mContentView: View? = null

    constructor(context: Context, viewLayoutId: Int) : this() {
        mContentView = LayoutInflater.from(context).inflate(viewLayoutId, null)
    }

    fun setContentView(view: View?) {
        mContentView = view
    }

    fun setText(viewId: Int, charSequence: CharSequence?) {

    }

    fun setOnClickListener(viewId: Int, onClickListener: View.OnClickListener?) {

    }

    fun getContentView(): View {
        return mContentView!!
    }

}