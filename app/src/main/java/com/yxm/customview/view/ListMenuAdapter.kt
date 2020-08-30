package com.yxm.customview.view

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.yxm.customview.R
import com.yxm.customview.basic.BaseMenuAdapter
import kotlinx.android.synthetic.main.activity_handler_test.view.*

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/8/30 9:07
 * @description
 */
open class ListMenuAdapter(context: Context) : BaseMenuAdapter() {

    private val mItems = arrayOf("品牌", "价格", "更多", "类型")
    private val mContext = context

    override fun getCount(): Int {
        return mItems.size
    }

    override fun getTabView(position: Int, parent: ViewGroup): View {
        val tabView = LayoutInflater.from(mContext).inflate(R.layout.list_data_tab_view, parent,false) as AppCompatTextView
        tabView.text = mItems[position]
        return tabView
    }

    override fun getMenuView(position: Int, parent: ViewGroup): View {
        val menuView = LayoutInflater.from(mContext).inflate(R.layout.list_data_tab_view, parent,false) as AppCompatTextView
        menuView.text = mItems[position]
        return menuView
    }

    override fun onMenuClose(tabView: View) {
        val tab = tabView as AppCompatTextView
        tab.setTextColor(Color.BLACK)
    }

    override fun onMenuOpen(tabView: View) {
        val tab = tabView as AppCompatTextView
        tab.setTextColor(Color.RED)
    }
}