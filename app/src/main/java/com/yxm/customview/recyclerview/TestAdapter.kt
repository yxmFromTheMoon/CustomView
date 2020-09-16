package com.yxm.customview.recyclerview

import android.content.Context
import android.widget.TextView
import com.yxm.customview.R

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/12 19:31
 * @description
 */
class TestAdapter(context: Context, layoutId: Int, data: List<String>)
    : CommonAdapter<String>(context, layoutId, data) {

    override fun convert(holder: ViewHolder, item: String, position: Int) {

    }
}