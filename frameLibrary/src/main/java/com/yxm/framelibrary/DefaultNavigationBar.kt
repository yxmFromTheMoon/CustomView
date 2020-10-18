package com.yxm.framelibrary

import android.R
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.yxm.baselibrary.navigation.AbsNavigationBar


/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/17 13:49
 * @description
 */
class DefaultNavigationBar(params: Builder.DefaultNavigationBarParams)
    : AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationBarParams>(params) {

    override fun bindLayoutId(): Int {
        return super.bindLayoutId()
    }

    override fun applyView() {
        super.applyView()
    }

    class Builder(context: Context, parent: ViewGroup)
        : AbsNavigationBar.Builder(context, parent) {

        var params: DefaultNavigationBarParams

        init {
            params = DefaultNavigationBarParams(context, parent)
        }

        fun setTitle(title: String): Builder {
            params.mTitle = title
            return this
        }

        fun setRightText(text: String): Builder {
            params.mRightText = text
            return this
        }

        fun setLeftIcon(resId: Int): Builder {
            params.mLeftIcon = resId
            return this
        }

        fun setRightIcon(resId: Int): Builder {
            params.mRightIcon = resId
            return this
        }

        override fun build(): DefaultNavigationBar {
            return DefaultNavigationBar(params)
        }

        class DefaultNavigationBarParams(context: Context, parent: ViewGroup)
            : AbsNavigationBarParams(context, parent) {
            var mRightIcon: Int = 0
            var mLeftIcon: Int = 0
            lateinit var mRightText: String
            lateinit var mTitle: String
        }
    }
}