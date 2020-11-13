package com.yxm.baselibrary.ui.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout

/**
 *@author: yxm
 *@time: 2020/11/13
 *@description:指示器Container
 */
class IndicatorContainerGroup @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0)
    : FrameLayout(context, attributeSet, defStyle) {

    private var mParams: LayoutParams

    val itemCount: Int
        get() {
            return mIndicatorContainer.childCount
        }
    private var mIndicatorContainer: LinearLayout = LinearLayout(context)

    init {
        addView(mIndicatorContainer)
        mParams = mIndicatorContainer.layoutParams as LayoutParams
    }

    fun getItemView(position: Int): View {
        return mIndicatorContainer.getChildAt(position)
    }

    fun addItemView(view: View) {
        mIndicatorContainer.addView(view)
    }

    /**
     * 添加底部指示器view
     * @param bottomTrackView View?
     */
    fun addBottomTrackView(bottomTrackView: View?) {
        bottomTrackView?.let {
            (it.layoutParams as LayoutParams).gravity = Gravity.BOTTOM
            addView(bottomTrackView)
        }
    }

}