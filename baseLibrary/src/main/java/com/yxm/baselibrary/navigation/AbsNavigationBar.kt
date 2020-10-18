package com.yxm.baselibrary.navigation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/17 13:37
 * @description 头部的builder基类
 */
abstract class AbsNavigationBar<T : AbsNavigationBar.Builder.AbsNavigationBarParams>(params: T)
    : INavigationBar {

    private var mParams = params

    init {
        createAndBindView()
    }

    fun getParams(): T {
        return mParams
    }

    /**
     * 创建和绑定view
     */
    private fun createAndBindView() {
        //1.创建view
        val view = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(), mParams.mParent, false)

        //2.添加
        mParams.mParent.addView(view, 0)
        applyView()
    }

    override fun bindLayoutId(): Int {

        return 0
    }

    override fun applyView() {
    }

    //
    abstract class Builder(context: Context, parent: ViewGroup) {

        abstract fun build(): AbsNavigationBar<*>

        open class AbsNavigationBarParams(context: Context, parent: ViewGroup) {
            val mContext = context
            val mParent = parent
        }
    }
}