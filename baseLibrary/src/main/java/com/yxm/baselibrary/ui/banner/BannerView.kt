package com.yxm.baselibrary.ui.banner

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.animation.AccelerateInterpolator
import android.view.animation.Interpolator
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.yxm.baselibrary.R
import com.yxm.baselibrary.Utils
import com.yxm.baselibrary.ui.BannerViewPager

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/11 19:51
 * @description
 */
class BannerView @JvmOverloads constructor(context: Context, val attributeSet: AttributeSet? = null,
                                           val defStyle: Int = 0) : RelativeLayout(context, attributeSet, defStyle) {

    private lateinit var mBannerViewPager: BannerViewPager
    private lateinit var mDescTv: TextView
    private lateinit var mIndicatorContainer: LinearLayout
    private var mDefaultSelectDrawable: Drawable? = null
    private var mDefaultNormalDrawable: Drawable? = null
    private var mIndicatorGravity: Int = 1
    private var mInterpolator: Interpolator? = AccelerateInterpolator()

    private lateinit var mBannerAdapter: BannerAdapter
    private var mCurrentPosition = 0
    private var mDescTextColor = Color.parseColor("#333333")
    private var mDescTextSize = Utils.dp2px(context, 12f)
    private var mDotSize = Utils.dp2px(context, 8f)
    private var mDotDistance = Utils.dp2px(context, 2f)
    private var mDefaultIndicatorBottomBackground = Color.parseColor("#00000000")

    init {
        inflate(context, R.layout.banner_layout, this)
        initAttributeSet()
        initView()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = (width * 8 / 3)
        setMeasuredDimension(width, height)
    }

    private fun initAttributeSet() {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.BannerView)
        mDefaultNormalDrawable = typedArray.getDrawable(R.styleable.BannerView_normalIndicatorDrawable)
        if (mDefaultNormalDrawable == null) {
            mDefaultNormalDrawable = ColorDrawable(Color.WHITE)
        }
        mDefaultSelectDrawable = typedArray.getDrawable(R.styleable.BannerView_selectIndicatorDrawable)
        if (mDefaultSelectDrawable == null) {
            mDefaultSelectDrawable = ColorDrawable(Color.RED)
        }
        mIndicatorGravity = typedArray.getInt(R.styleable.BannerView_indicatorGravity, mIndicatorGravity)
        when (mIndicatorGravity) {
            0 -> {
                mIndicatorGravity = Gravity.START
            }
            1 -> {
                mIndicatorGravity = Gravity.CENTER
            }
            2 -> {
                mIndicatorGravity = Gravity.END
            }
        }
        mDescTextColor = typedArray.getColor(R.styleable.BannerView_descTextColor, mDescTextColor)
        mDescTextSize = typedArray.getDimension(R.styleable.BannerView_descTextSize, mDescTextSize.toFloat()).toInt()
        mDefaultIndicatorBottomBackground = typedArray.getColor(R.styleable.BannerView_bottomBackground, mDefaultIndicatorBottomBackground)
        mDotSize = typedArray.getDimension(R.styleable.BannerView_dotSize, mDotSize.toFloat()).toInt()
        mDotDistance = typedArray.getDimension(R.styleable.BannerView_dotDistance, mDotDistance.toFloat()).toInt()
        typedArray.recycle()
    }

    private fun initView() {
        mBannerViewPager = findViewById(R.id.banner_vp)
        mDescTv = findViewById(R.id.banner_desc_tv)
        mIndicatorContainer = findViewById(R.id.indicator_container)
        findViewById<RelativeLayout>(R.id.banner_bottom_rl).setBackgroundColor(mDefaultIndicatorBottomBackground)

        mDescTv.textSize = mDescTextSize.toFloat()
        mDescTv.setTextColor(mDescTextColor)
        mIndicatorContainer.gravity = mIndicatorGravity
    }

    fun setAdapter(adapter: BannerAdapter): BannerView {
        mBannerAdapter = adapter
        mBannerViewPager.setAdapter(adapter)
        initIndicator()

        mBannerViewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                pageSelect(position)
            }
        })
        val desc = mBannerAdapter.getBannerDesc(0)
        mDescTv.text = desc
        return this
    }

    /**
     * 设置滚动时的差值器
     * @param interpolator Interpolator
     */
    fun setInterpolator(interpolator: Interpolator): BannerView {
        mInterpolator = interpolator
        mBannerViewPager.setInterpolator(interpolator)
        return this
    }

    private fun pageSelect(position: Int) {
        //要把之前的指示器变为未选中，把当前选中的变为选中状态
        val preDotIndicatorView = mIndicatorContainer.getChildAt(mCurrentPosition) as DotIndicatorView

        preDotIndicatorView.setDrawable(mDefaultNormalDrawable)

        mCurrentPosition = position % mBannerAdapter.getCount()

        val currentDotIndicatorView = mIndicatorContainer.getChildAt(mCurrentPosition) as DotIndicatorView

        currentDotIndicatorView.setDrawable(mDefaultSelectDrawable)

        val currentDesc = mBannerAdapter.getBannerDesc(mCurrentPosition)
        mDescTv.text = currentDesc
    }

    private fun initIndicator() {
        val count = mBannerAdapter.getCount()

        for (i in 0 until count) {
            val dotView = DotIndicatorView(context)
            val lp = LinearLayout.LayoutParams(mDotSize, mDotSize)
            lp.rightMargin = mDotDistance
            lp.leftMargin = mDotDistance

            dotView.layoutParams = lp

            if (i == 0) {
                dotView.setDrawable(mDefaultSelectDrawable)
            } else {
                dotView.setDrawable(mDefaultNormalDrawable)
            }
            mIndicatorContainer.addView(dotView)
        }
    }

    fun start() {
        mBannerViewPager.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

}