package com.yxm.baselibrary.ui

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/11 7:45
 * @description
 */
class BannerViewPager @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null)
    : ViewPager(context, attributeSet) {
    private lateinit var mBannerAdapter: BannerAdapter

    private val SCROLL_MSG = 0x001
    private val mCountDownTime = 3000L

    private var mHandler: Handler? = Handler {
        currentItem += 1
        return@Handler false
    }

    fun start() {
        mHandler?.removeMessages(SCROLL_MSG)

        mHandler?.sendEmptyMessageDelayed(SCROLL_MSG, mCountDownTime)
    }

    fun setAdapter(adapter: BannerAdapter) {
        this.mBannerAdapter = adapter
        setAdapter(BannerPagerAdapter())
    }

    override fun onDetachedFromWindow() {
        mHandler?.removeMessages(SCROLL_MSG)
        mHandler = null
        super.onDetachedFromWindow()
    }

    inner class BannerPagerAdapter : PagerAdapter() {
        override fun getCount(): Int {
            //无限轮播
            return Int.MAX_VALUE
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        //创建条目的方法
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            //adapter设计模式让用户自定义
            //添加到viewpager里
            val view = mBannerAdapter.getView(position)
            container.addView(view)
            return view
        }

        //销毁条目调用的方法
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }
}