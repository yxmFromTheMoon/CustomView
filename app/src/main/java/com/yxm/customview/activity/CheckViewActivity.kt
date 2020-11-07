package com.yxm.customview.activity

import android.widget.Button
import com.yxm.customview.R
import com.yxm.baselibrary.base.BaseActivity
import com.yxm.customview.view.CheckView
import com.yxm.framelibrary.BaseSkinActivity
import kotlinx.android.synthetic.main.activity_check_view.*

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/27 13:04
 * @Description
 */
class CheckViewActivity: BaseSkinActivity() {

    private lateinit var checkView:CheckView
    private lateinit var testButton: Button

    override fun getLayoutId(): Int {
        return R.layout.activity_check_view
    }

    override fun initView() {
        checkView = check_view
        testButton = test_button
    }

    override fun initListener() {
        testButton.setOnClickListener {
            checkView.check()
        }
    }

    override fun initData() {
    }
}