package com.yxm.customview.enter

import android.content.Context
import android.widget.TextView
import com.yxm.customview.R
import com.yxm.baselibrary.recyclerview.CommonAdapter
import com.yxm.baselibrary.recyclerview.ViewHolder

/**
 *@author yxm
 *@time 2020/9/16 4:07 PM
 *@description
 */
class EnterListAdapterV2(context: Context, data: List<ButtonBean>)
    : CommonAdapter<ButtonBean>(context, R.layout.item_enter, data) {

    override fun convert(holder: ViewHolder, item: ButtonBean, position: Int) {
        val button = holder.getView<TextView>(R.id.enter_button)
        button.text = item.text
        button.tag = item.tag
    }

}