package com.yxm.baselibrary.navigation

import android.R
import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yxm.baselibrary.navigation.AbsNavigationBarJava.Builder.AbsNavigationParams

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/18 16:17
 * @description
 */
abstract class AbsNavigationBarJava<P : AbsNavigationParams?>(val params: P) : INavigationBar {
    private var mNavigationView: View? = null

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    protected fun setText(viewId: Int, text: String?) {
        val tv = findViewById<TextView>(viewId)
        if (!TextUtils.isEmpty(text)) {
            tv.visibility = View.VISIBLE
            tv.text = text
        }
    }

    /**
     * 设置点击
     *
     * @param viewId
     * @param listener
     */
    protected fun setOnClickListener(viewId: Int, listener: View.OnClickListener?) {
        findViewById<View>(viewId).setOnClickListener(listener)
    }

    fun <T : View?> findViewById(viewId: Int): T {
        return mNavigationView!!.findViewById<View>(viewId) as T
    }

    /**
     * 绑定和创建View
     */
    private fun createAndBindView() {
        // 1. 创建View
        if (params!!.mParent == null) {
            // 获取activity的根布局，View源码
            val activityRoot = (params.mContext as Activity)
                    .findViewById<ViewGroup>(R.id.content)
            params.mParent = activityRoot.getChildAt(0) as ViewGroup
            Log.e("TAG", params.mParent.toString() + "")
        }

        // 处理Activity的源码，后面再去看
        if (params.mParent == null) {
            return
        }
        mNavigationView = LayoutInflater.from(params.mContext).inflate(bindLayoutId(), params.mParent, false) // 插件换肤

        // 2.添加
        params.mParent!!.addView(mNavigationView, 0)
        applyView()
    }

    // Builder  仿照系统写的， 套路，活  AbsNavigationBar  Builder  参数Params
    abstract class Builder(context: Context?, parent: ViewGroup?) {
        abstract fun builder(): AbsNavigationBarJava<*>?

        open class AbsNavigationParams(var mContext: Context, var mParent: ViewGroup?)
    }

    init {
        createAndBindView()
    }
}