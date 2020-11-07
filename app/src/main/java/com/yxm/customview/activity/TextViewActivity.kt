package com.yxm.customview.activity

import android.view.View
import com.yxm.customview.R
import com.yxm.baselibrary.base.BaseActivity
import com.yxm.baselibrary.ioc.OnClick
import com.yxm.baselibrary.ioc.ViewById
import com.yxm.customview.showToast
import com.yxm.customview.view.TextView
import com.yxm.framelibrary.BaseSkinActivity
import kotlinx.android.synthetic.main.activity_text_view.*

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/27 13:04
 * @Description
 */
class TextViewActivity : BaseSkinActivity() {

    @ViewById(R.id.text_view)
    private lateinit var mTextView:TextView

    override fun getLayoutId(): Int {
        return R.layout.activity_text_view
    }

    override fun initView() {

    }

    override fun initListener() {
//        mTextView.setOnClickListener {
//            "我是自定义TextView".showToast()
//            mTextView.setText("我是自定义TextView")
//        }
    }

    @OnClick(R.id.text_view)
    fun onClick(view: View) {
        if (view.id == R.id.text_view) {
            "我是自定义TextView".showToast()
            mTextView.setText("我是自定义TextView")
        }
    }

    override fun initData() {
    }
}