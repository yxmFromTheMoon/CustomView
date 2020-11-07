package com.yxm.customview.activity

import android.widget.Button
import com.yxm.customview.R
import com.yxm.baselibrary.base.BaseActivity
import com.yxm.customview.gone
import com.yxm.customview.view.BouquetLoadingView
import com.yxm.framelibrary.BaseSkinActivity
import kotlinx.android.synthetic.main.activity_bouquet_loading_view.*

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/8/30 16:34
 * @description
 */
class BouquetLoadingViewActivity : BaseSkinActivity() {

    private lateinit var mLoadingView: BouquetLoadingView
    private lateinit var mGoneButton: Button

    override fun getLayoutId(): Int {
        return R.layout.activity_bouquet_loading_view
    }

    override fun initView() {
        mLoadingView = bouquet_loading_view

        mGoneButton = gone_button

    }

    override fun initListener() {

        mGoneButton.setOnClickListener {
            mLoadingView.gone()
        }
    }

    override fun initData() {

    }
}