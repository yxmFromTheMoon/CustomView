package com.yxm.baselibrary.ui.indicator

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.yxm.baselibrary.Utils

/**
 *@author: yxm
 *@time: 2020/11/13
 *@description:指示器Container
 */
class IndicatorContainerGroup @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0)
    : FrameLayout(context, attributeSet, defStyle) {

    private lateinit var mParams: LayoutParams
    private var mBottomTrackView: View? = null
    private var mItemWidth = 0
    private var mInitLeftMargin = 0

    val itemCount: Int
        get() {
            return mIndicatorContainer.childCount
        }
    private var mIndicatorContainer: LinearLayout = LinearLayout(context)

    init {
        addView(mIndicatorContainer)
        val params = mIndicatorContainer.layoutParams as LayoutParams
        params.bottomMargin = 20
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
    fun addBottomTrackView(bottomTrackView: View?, itemWidth: Int) {
        bottomTrackView?.let {
            mBottomTrackView = it
            mItemWidth = itemWidth
            //要在底部，宽度是一个条目的宽度
            mParams = it.layoutParams as LayoutParams
            mParams.gravity = Gravity.BOTTOM
            var trackWidth = mParams.width
            if (mParams.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                trackWidth = mItemWidth
            }
            if (trackWidth > mItemWidth) {
                trackWidth = mItemWidth
            }
            mParams.width = trackWidth
            mInitLeftMargin = (mItemWidth - trackWidth) / 2
            mParams.leftMargin = mInitLeftMargin
            addView(it)
        }
    }

    /**
     * 切换时滑动
     * @param position Int
     * @param positionOffset Float
     */
    fun scrollBottomTrackView(position: Int, positionOffset: Float) {
        mBottomTrackView?.let {
            val leftMargin =((position + positionOffset) * mItemWidth).toInt()
            mParams.leftMargin = (leftMargin + mInitLeftMargin)
            it.layoutParams = mParams
        }
    }

    /**
     * 点击Tab时滑动
     * @param position Int
     */
    fun smoothBottomTrackView(position: Int) {
        mBottomTrackView?.let { view ->
            //计算要移动的偏移量
            val finalLeftMargin = position * mItemWidth.toFloat() + mInitLeftMargin

            val currentMargin = mParams.leftMargin.toFloat()

            val animator = ObjectAnimator.ofFloat(currentMargin, finalLeftMargin)
            animator.duration = 300
            animator.addUpdateListener {
                val currentLeftMargin = it.animatedValue as Float
                mParams.leftMargin = currentLeftMargin.toInt()
                view.layoutParams = mParams
            }
            animator.interpolator = DecelerateInterpolator()
            animator.start()
        }
    }

}