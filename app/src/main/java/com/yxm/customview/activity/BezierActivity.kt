package com.yxm.customview.activity

import android.widget.TextView
import com.yxm.customview.R
import com.yxm.baselibrary.base.BaseActivity
import com.yxm.baselibrary.ioc.OnClick
import com.yxm.customview.bezier.BubbleViewTouchListener
import com.yxm.customview.bezier.MessageBubbleView
import com.yxm.customview.showToast
import kotlinx.android.synthetic.main.activity_bezier.*

/**
 *@author yxm
 *@time 2020/9/16 3:57 PM
 *@description 贝塞尔曲线
 */
class BezierActivity : BaseActivity() {

    private lateinit var mTv: TextView


    override fun getLayoutId(): Int {
        return R.layout.activity_bezier
    }

    override fun initView() {
        mTv = text_view
    }

    override fun initListener() {
        MessageBubbleView.attach(mTv, object : BubbleViewTouchListener.BubbleDisappearListener {
            override fun dismiss() {
                "消失".showToast()
            }
        })
    }

    override fun initData() {
    }
}