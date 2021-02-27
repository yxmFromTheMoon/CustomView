package com.yxm.customview.enter

import android.content.Context
import android.view.ViewTreeObserver
import android.widget.TextView
import com.yxm.customview.R
import com.yxm.baselibrary.recyclerview.CommonAdapter
import com.yxm.baselibrary.recyclerview.ViewHolder
import com.yxm.customview.LauncherTimer

/**
 *@author yxm
 *@time 2020/9/16 4:07 PM
 *@description
 */
class EnterListAdapterV2(context: Context, data: List<ButtonBean>)
    : CommonAdapter<ButtonBean>(context, R.layout.item_enter, data), ViewTreeObserver.OnPreDrawListener {
    private var isRecord = false

    override fun convert(holder: ViewHolder, item: ButtonBean, position: Int) {
        if (position == 0 && !isRecord) {
            isRecord = true
            holder.itemView.viewTreeObserver.addOnPreDrawListener {
                holder.itemView.viewTreeObserver.removeOnPreDrawListener(this)
                LauncherTimer.endRecord("First Item")
                return@addOnPreDrawListener true
            }
        }
        val button = holder.getView<TextView>(R.id.enter_button)
        button.text = item.text
        button.tag = item.tag
    }

    override fun onPreDraw(): Boolean {
        return true
    }

}