package com.yxm.customview.activity

import com.yxm.customview.R
import com.yxm.customview.basic.BaseActivity
import com.yxm.customview.showToast
import com.yxm.customview.view.TextView
import kotlinx.android.synthetic.main.activity_text_view.*

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/27 13:04
 * @Description
 */
class TextViewActivity :BaseActivity(){
    private lateinit var mTextView:TextView

    override fun getLayoutId(): Int {
        return R.layout.activity_text_view
    }

    override fun initView() {
        mTextView = text_view
    }

    override fun initListener() {
        mTextView.setOnClickListener {
            "我是自定义TextView".showToast()
            mTextView.setText("我是自定义TextView")
        }
    }

    override fun initData() {
    }
}