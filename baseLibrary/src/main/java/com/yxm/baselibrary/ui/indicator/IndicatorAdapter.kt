package com.yxm.baselibrary.ui.indicator

import android.view.View
import android.view.ViewGroup

/**
 * Created by Myron at 2020/11/13 7:38
 * @email yxmbest@163.com
 * @description
 */
abstract class IndicatorAdapter {
    abstract fun getCount(): Int

    abstract fun getView(position: Int, parent: ViewGroup): View
}