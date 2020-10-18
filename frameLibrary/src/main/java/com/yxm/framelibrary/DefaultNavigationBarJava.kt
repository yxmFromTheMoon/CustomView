package com.yxm.framelibrary

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.yxm.baselibrary.navigation.AbsNavigationBarJava
import com.yxm.framelibrary.DefaultNavigationBarJava.Builder.DefaultNavigationParams

/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/2/26.
 * Version 1.0
 * Description:
 */
class DefaultNavigationBarJava(params: DefaultNavigationParams?)
    : AbsNavigationBarJava<DefaultNavigationParams?>(params) {

    override fun bindLayoutId(): Int {
        return R.layout.title_bar
    }

    override fun applyView() {
        // 绑定效果
        setText(R.id.title, params!!.mTitle)
        setText(R.id.right_text, params.mRightText)
        setOnClickListener(R.id.right_text, params.mRightClickListener)
        // 左边 要写一个默认的  finishActivity
        setOnClickListener(R.id.back, params.mLeftClickListener)
    }

    class Builder : AbsNavigationBarJava.Builder {
        var P: DefaultNavigationParams

        constructor(context: Context?, parent: ViewGroup?) : super(context, parent) {
            P = DefaultNavigationParams(context, parent)
        }

        constructor(context: Context?) : super(context, null) {
            P = DefaultNavigationParams(context, null)
        }

        override fun builder(): DefaultNavigationBarJava? {
            return DefaultNavigationBarJava(P)
        }

        // 1. 设置所有效果
        fun setTitle(title: String?): Builder {
            P.mTitle = title
            return this
        }

        fun setRightText(rightText: String?): Builder {
            P.mRightText = rightText
            return this
        }

        /**
         * 设置右边的点击事件
         */
        fun setRightClickListener(rightListener: View.OnClickListener?): Builder {
            P.mRightClickListener = rightListener
            return this
        }

        /**
         * 设置左边的点击事件
         */
        fun setLeftClickListener(rightListener: View.OnClickListener): Builder {
            P.mLeftClickListener = rightListener
            return this
        }

        /**
         * 设置右边的图片
         */
        fun setRightIcon(rightRes: Int): Builder {
            return this
        }

        class DefaultNavigationParams(context: Context?, parent: ViewGroup?)
            : AbsNavigationParams(context!!, parent) {
            // 2.所有效果放置
            var mTitle: String? = null
            var mRightText: String? = null

            // 后面还有一些通用的
            var mRightClickListener: View.OnClickListener? = null
            var mLeftClickListener = View.OnClickListener { // 关闭当前Activity
                (mContext as Activity).finish()
            }
        }
    }
}