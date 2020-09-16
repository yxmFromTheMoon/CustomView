package com.yxm.customview.enter

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.yxm.customview.R
import com.yxm.customview.recyclerview.CommonAdapter
import com.yxm.customview.recyclerview.CommonRecyclerAdapter
import com.yxm.customview.recyclerview.ViewHolder
import com.yxm.customview.showToast

/**
 *@author yxm
 *@time 2020/9/16 4:07 PM
 *@description
 */
class EnterListAdapterV2(context: Context, data: List<ButtonBean>)
    : CommonRecyclerAdapter<ButtonBean>(context, data, R.layout.item_enter) {

    override fun convert(holder: ViewHolder, item: ButtonBean, position: Int) {
        val button = holder.getView<TextView>(R.id.enter_button)
        button.text = item.text
        button.tag = item.tag
    }

}