package com.yxm.baselibrary.ui

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.yxm.baselibrary.ui.banner.BannerAdapter
import com.yxm.baselibrary.ui.banner.BannerScroll

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/11 7:45
 * @description
 */
class BannerViewPager @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null)
    : ViewPager(context, attributeSet) {

    companion object {
        private const val SCROLL_MESSAGE = 0x001
        private const val DEFAULT_SCROLL_DURATION = 2000L
    }

    private lateinit var mBannerAdapter: BannerAdapter
    private lateinit var mData: List<String>
    private var mDuration = DEFAULT_SCROLL_DURATION
    private var mAutoPlay = true
    private var mInterpolator: Interpolator? = null
    private val mContext = context
    private var mFirstLayout: Boolean = true

    init {
        hookScroller()
    }

    /**
     * 通过反射获取到mScroll改变viewpager的切换速率
     */
    private fun hookScroller() {
        try {
            val bannerScroll = BannerScroll(context, mInterpolator)
            bannerScroll.setScrollDuration(mDuration)
            //通过反射拿到mScroll属性
            val field = ViewPager::class.java.getDeclaredField("mScroller")
            field.isAccessible = true
            //设置该属性，第一个参数是要设置该属性的对象，第二参数是要设置的属性，其实相当于this.setScroller(bannerScroll)
            field.set(this, bannerScroll)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun hookFirstLayout() {
        try {
            val firstLayout = ViewPager::class.java.getDeclaredField("mFirstLayout")
            firstLayout.isAccessible = true
            firstLayout.set(this, mFirstLayout)
            currentItem = currentItem
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
    }

    fun setFirstLayout(firstLayout: Boolean) {
        mFirstLayout = firstLayout
    }

    private var mHandler: Handler? = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (msg.what == SCROLL_MESSAGE) {
                currentItem += 1
                start()
            }
        }
    }

    /**
     * 是否自动播放
     * @param isAutoPlay Boolean
     * @return BannerViewPager
     */
    fun setAutoPlay(isAutoPlay: Boolean): BannerViewPager {
        mAutoPlay = isAutoPlay
        return this
    }

    /**
     * 设置滚动时的差值器
     * @param interpolator Interpolator
     */
    fun setInterpolator(interpolator: Interpolator):BannerViewPager {
        mInterpolator = interpolator
        return this
    }

    /**
     * 切换时长
     * @param duration Long
     */
    fun setScrollDuration(duration: Long): BannerViewPager {
        mDuration = duration
        return this
    }

    /**
     * 设置数据源
     * @param datas List<String>
     */
    fun setData(datas: List<String>): BannerViewPager {
        mData = datas
        return this
    }

    fun start() {
        if (mAutoPlay) {
            mHandler?.removeMessages(SCROLL_MESSAGE)
            mHandler?.sendEmptyMessageDelayed(SCROLL_MESSAGE, mDuration)
        }
    }

    /**
     * 设置adapter
     * @param adapter BannerAdapter
     * @return BannerViewPager
     */
    fun setAdapter(adapter: BannerAdapter): BannerViewPager {
        this.mBannerAdapter = adapter
        setAdapter(BannerPagerAdapter())
        return this
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        hookFirstLayout()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mHandler?.removeMessages(SCROLL_MESSAGE)
        mHandler = null
    }

    fun getRealPosition(isCanLoop: Boolean, position: Int, pageSize: Int): Int {
        return if (isCanLoop) (position - 1 + pageSize) % pageSize else (position + pageSize) % pageSize
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
            val view = mBannerAdapter.getView(getRealPosition(true,position,mBannerAdapter.getCount()))
            container.addView(view)
            return view
        }

        //销毁条目调用的方法
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }
}