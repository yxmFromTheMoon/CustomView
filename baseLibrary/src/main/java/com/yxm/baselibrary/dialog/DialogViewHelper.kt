package com.yxm.baselibrary.dialog

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/13 20:43
 * @description
 */
class DialogViewHelper() {

    var mContentView: View? = null
    private val mViews = SparseArray<View?>()

    constructor(context: Context, viewLayoutId: Int) : this() {
        mContentView = LayoutInflater.from(context).inflate(viewLayoutId, null)
    }

    fun setContentView(view: View?) {
        mContentView = view
    }

    fun setText(viewId: Int, charSequence: CharSequence?) {
        getView<TextView>(viewId).text = charSequence
    }

    fun setOnClickListener(viewId: Int, onClickListener: View.OnClickListener?) {
        getView<View>(viewId).setOnClickListener(onClickListener)
    }

    private fun <T : View> getView(viewId: Int): T {
        var view = mViews[viewId]
        if (view == null) {
            view = mContentView?.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    fun getContentView(): View {
        return mContentView!!
    }

}