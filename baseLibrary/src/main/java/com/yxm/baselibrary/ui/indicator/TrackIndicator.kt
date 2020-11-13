package com.yxm.baselibrary.ui.indicator

import android.content.Context
import android.util.AttributeSet
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import java.lang.IllegalArgumentException
import kotlin.math.max

/**
 * Created by Myron at 2020/11/13 7:33
 * @email yxmbest@163.com
 * @description
 */
class TrackIndicator @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0)
    : HorizontalScrollView(context, attributeSet, defStyle) {
    private var mIndicatorContainer: LinearLayout = LinearLayout(context)
    private lateinit var mAdapter: IndicatorAdapter
    private var mItemWidth: Int = 0
    private var mTabVisibleNum = 0

    init {
        addView(mIndicatorContainer)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (changed) {
            mItemWidth = getItemWidth()
            for (i in 0 until mIndicatorContainer.childCount) {
                val lp = mIndicatorContainer.getChildAt(i).layoutParams
                lp.width = mItemWidth
                mIndicatorContainer.getChildAt(i).layoutParams = lp
            }
        }
    }

    private fun getItemWidth(): Int {
        var itemWidth: Int
        var allWidth: Int
        var maxWidth = 0
        val parentWidth = width
        if (mTabVisibleNum != 0) {
            return parentWidth / mTabVisibleNum
        }
        for (i in 0 until mIndicatorContainer.childCount) {
            val currentItemWidth = mIndicatorContainer.getChildAt(i).width
            maxWidth = max(currentItemWidth, maxWidth)
        }
        itemWidth = maxWidth
        allWidth = itemWidth * mAdapter.getCount()
        if (allWidth < parentWidth) {
            itemWidth = parentWidth / mAdapter.getCount()
        }
        return itemWidth
    }

    fun setAdapter(adapter: IndicatorAdapter?) {
        if (adapter == null) {
            throw IllegalArgumentException("adapter is null")
        }
        mAdapter = adapter
        initIndicator()
    }

    private fun initIndicator() {
        val count = mAdapter.getCount()
        for (i in 0 until count) {
            val view = mAdapter.getView(i, this)
            mIndicatorContainer.addView(view)
        }
    }

}