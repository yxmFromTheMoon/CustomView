package com.yxm.customview.activity

import com.yxm.customview.R
import com.yxm.baselibrary.base.BaseActivity
import com.yxm.customview.view.ListDataScreenView
import com.yxm.customview.view.ListMenuAdapter
import kotlinx.android.synthetic.main.activity_list_data_screen.*

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/8/30 16:34
 * @description
 */
class ListDataScreenActivity : BaseActivity() {

    private lateinit var mListDataView: ListDataScreenView

    override fun getLayoutId(): Int {
        return R.layout.activity_list_data_screen
    }

    override fun initView() {
        mListDataView = list_data_screen_view
        mListDataView.setAdapter(ListMenuAdapter(mContext))
    }

    override fun initListener() {
    }

    override fun initData() {
    }
}