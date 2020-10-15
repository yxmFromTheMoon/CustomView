package com.yxm.baselibrary.dialog

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import java.lang.ref.WeakReference

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/13 20:43
 * @description
 */
class DialogViewHelper() {

    var mContentView: View? = null
    //防止内存泄漏
    private val mViews = SparseArray<WeakReference<View?>>()

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

    fun <T : View> getView(viewId: Int): T {
        val weakReference = mViews[viewId]
        var view: View? = null
        if (weakReference != null) {
            view = weakReference.get()
        }
        if (view == null) {
            view = mContentView?.findViewById(viewId)
            if (view != null) {
                mViews.put(viewId, WeakReference(view))
            }
        }
        return view as T
    }

    fun getContentView(): View {
        return mContentView!!
    }

}