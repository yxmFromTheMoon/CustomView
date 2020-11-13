package com.yxm.customview.activity

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.yxm.baselibrary.ui.indicator.IndicatorAdapter
import com.yxm.baselibrary.ui.indicator.TrackIndicator
import com.yxm.customview.fragment.ItemFragment
import com.yxm.customview.R
import com.yxm.baselibrary.ui.indicator.ColorTrackTextView
import com.yxm.framelibrary.BaseSkinActivity
import kotlinx.android.synthetic.main.activity_view_pager.*
import java.lang.Exception

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/20 14:02
 * @Description
 */
class ViewPagerActivity : BaseSkinActivity() {

    private val titles = arrayOf("直播", "搞笑","段友秀", "音乐", "影视", "图片","游戏","创意","萌宠","艺术")
    private lateinit var viewPager: ViewPager
    private lateinit var mIndicatorsContainer: TrackIndicator
    private var mIndicators: MutableList<TextView> = ArrayList()

    override fun getLayoutId(): Int {
        return R.layout.activity_view_pager
    }

    override fun initView() {
        viewPager = view_pager
        mIndicatorsContainer = indicator_view
    }

    override fun initListener() {

    }

    override fun initData() {
        initIndicator()
        initAdapter()
    }

    private fun initAdapter() {
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager, 1) {
            override fun getItem(position: Int): Fragment {
                return ItemFragment.newInstance(titles[position])
            }

            override fun getCount(): Int {
                return titles.size
            }
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //position 代表当前的位置
                //positionOffset 代表滚动的 0 -1 的值(百分比)
                //1.左边 位置position
//                val left = mIndicators[position]
//                left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT)
//                left.setProgress(1 - positionOffset)
//
//                try {
//                    val right = mIndicators[position + 1]
//                    right.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT)
//                    right.setProgress(positionOffset)
//                } catch (e: Exception) {
//
//                }

            }

            override fun onPageSelected(position: Int) {
            }
        })
    }

    private fun initIndicator() {
        mIndicatorsContainer.setAdapter(object : IndicatorAdapter() {
            override fun getCount(): Int {
                return titles.size
            }

            override fun getView(position: Int, parent: ViewGroup): View {
                //动态添加颜色跟踪的TextView
                val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                params.weight = 1f
                val colorTrackTextView = TextView(this@ViewPagerActivity)
                colorTrackTextView.textSize = 20f
                //colorTrackTextView.setChangeColor(Color.RED)
                colorTrackTextView.gravity = Gravity.CENTER
                colorTrackTextView.text = titles[position]
                colorTrackTextView.layoutParams = params
                mIndicators.add(colorTrackTextView)
                return colorTrackTextView
            }
        },viewPager)
    }
}