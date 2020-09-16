package com.yxm.customview.enter

import android.content.Context
import android.widget.Button
import com.yxm.customview.R
import com.yxm.customview.recyclerview.CommonAdapter
import com.yxm.customview.recyclerview.ViewHolder

/**
 *@author lm
 *@time 2020/9/16 4:07 PM
 *@description
 */
class EnterListAdapterV2(context: Context, layoutId: Int, data: List<ButtonBean>)
    : CommonAdapter<ButtonBean>(context, layoutId, data) {

    override fun convert(holder: ViewHolder, item: ButtonBean, position: Int) {
        val button = holder.getView<Button>(R.id.enter_button)
        button.text = item.text
        button.tag = item.tag
    }

}