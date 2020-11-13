package com.yxm.baselibrary.ui.indicator

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.yxm.baselibrary.R
import java.lang.IllegalArgumentException
import kotlin.math.max

/**
 * Created by Myron at 2020/11/13 7:33
 * @email yxmbest@163.com
 * @description
 */
class TrackIndicator @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0)
    : HorizontalScrollView(context, attributeSet, defStyle), ViewPager.OnPageChangeListener {
    private var mCurrentPosition: Int = 0
    private var mLastPosition: Int = 0
    private var mIndicatorContainer: LinearLayout = LinearLayout(context)
    private lateinit var mAdapter: IndicatorAdapter
    private var mItemWidth: Int = 0
    private var mTabVisibleNum = 0
    private var mViewPager: ViewPager? = null
    private var mContext: Context = context
    private var mSelectTabColor = Color.RED
    private var mNormalTabColor = Color.BLACK

    init {
        addView(mIndicatorContainer)
        initAttribute(attributeSet)
    }

    /**
     * 初始化自定义属性
     * @param attributeSet AttributeSet?
     */
    private fun initAttribute(attributeSet: AttributeSet?) {
        val tp = mContext.obtainStyledAttributes(attributeSet, R.styleable.TrackIndicator)
        mTabVisibleNum = tp.getInt(R.styleable.TrackIndicator_tabVisibleNum, 0)
        mSelectTabColor = tp.getColor(R.styleable.TrackIndicator_selectTabColor,mSelectTabColor)
        mNormalTabColor = tp.getColor(R.styleable.TrackIndicator_normalTabColor,mNormalTabColor)
        tp.recycle()
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

    /**
     * 获取item的宽度
     * @return Int
     */
    private fun getItemWidth(): Int {
        var itemWidth: Int
        val allWidth: Int
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

    fun setAdapter(adapter: IndicatorAdapter?, viewPager: ViewPager) {
        if (adapter == null) {
            throw IllegalArgumentException("adapter is null")
        }
        mAdapter = adapter
        mViewPager = viewPager
        mViewPager?.addOnPageChangeListener(this)
        initIndicator()
    }

    private fun initIndicator() {
        val count = mAdapter.getCount()
        for (i in 0 until count) {
            val view = mAdapter.getView(i, this)
            mIndicatorContainer.addView(view)
            if (mViewPager != null) {
                setItemClick(view, i)
            }
        }
        mAdapter.highLightTab(mCurrentPosition,mIndicatorContainer.getChildAt(mCurrentPosition))
    }

    private fun setItemClick(view: View, position: Int) {
        view.setOnClickListener {
            mViewPager?.currentItem = position
        }
    }

    /**
     * 始终将当前的tab居中
     * @param position Int
     * @param positionOffset Float
     */
    private fun scrollToCenter(position: Int, positionOffset: Float) {
        val totalScroll = (position + positionOffset) * mItemWidth
        val offsetScroll = (width - mItemWidth) / 2
        val finalScroll = totalScroll - offsetScroll
        scrollTo(finalScroll.toInt(), 0)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        scrollToCenter(position, positionOffset)
    }

    override fun onPageSelected(position: Int) {
        //将前一个位置变为默认
        mLastPosition = mCurrentPosition
        mAdapter.restoreTab(mLastPosition,mIndicatorContainer.getChildAt(mLastPosition))
        mCurrentPosition = position
        mAdapter.highLightTab(mCurrentPosition,mIndicatorContainer.getChildAt(mCurrentPosition))
    }

}