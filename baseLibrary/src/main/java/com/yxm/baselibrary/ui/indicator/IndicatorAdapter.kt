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

    //选中tab高亮
    open fun highLightTab(position: Int, view: View) {

    }

    //为选中tab变为默认
    open fun restoreTab(position: Int, view: View) {}

    open fun getBottomTrackView(): View? {
        return null
    }
}