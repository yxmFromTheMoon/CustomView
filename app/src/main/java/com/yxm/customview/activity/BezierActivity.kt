package com.yxm.customview.activity

import com.yxm.customview.R
import com.yxm.customview.basic.BaseActivity
import com.yxm.customview.bezier.MessageBubbleView
import kotlinx.android.synthetic.main.activity_bezier.*

/**
 *@author yxm
 *@time 2020/9/16 3:57 PM
 *@description 贝塞尔曲线
 */
class BezierActivity:BaseActivity() {

    private lateinit var mBezierView:MessageBubbleView

    override fun getLayoutId(): Int {
        return R.layout.activity_bezier
    }

    override fun initView() {
        mBezierView = message_bubble_view
    }

    override fun initListener() {
    }

    override fun initData() {
    }
}