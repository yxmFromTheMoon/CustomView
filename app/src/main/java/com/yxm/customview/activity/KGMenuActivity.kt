package com.yxm.customview.activity

import com.yxm.customview.R
import com.yxm.baselibrary.base.BaseActivity
import com.yxm.customview.showToast
import kotlinx.android.synthetic.main.activity_kgslide_menu.*
import kotlinx.android.synthetic.main.kg_content.view.*
import kotlinx.android.synthetic.main.kg_menu.view.*

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/7/5 11:58
 * @description
 */
class KGMenuActivity: BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_kgslide_menu
    }

    override fun initView() {
    }

    override fun initListener() {
        content_layout.content.setOnClickListener {
            "Content".showToast()
        }
        menu_layout.menu.setOnClickListener {
            "Menu".showToast()
        }
    }

    override fun initData() {
    }
}