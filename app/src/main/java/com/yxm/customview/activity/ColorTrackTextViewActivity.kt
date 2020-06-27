package com.yxm.customview.activity

import android.animation.ValueAnimator
import android.widget.Button
import com.yxm.customview.R
import com.yxm.customview.basic.BaseActivity
import com.yxm.customview.fragment.ItemFragment
import com.yxm.customview.utils.Utils
import com.yxm.customview.view.ColorTrackTextView
import kotlinx.android.synthetic.main.activity_color_track_view.*

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/27 13:05
 * @Description
 */
class ColorTrackTextViewActivity : BaseActivity() {

    private lateinit var leftToRight: Button
    private lateinit var rightToLeft: Button
    private lateinit var colorTrackView: ColorTrackTextView
    private var text: String? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_color_track_view
    }

    override fun initView() {
        text = intent.getStringExtra(ItemFragment.TEXT)

        leftToRight = left_to_right
        rightToLeft = right_to_left
        colorTrackView = colorTrackTextView
        colorTrackView.text = text
    }

    override fun initListener() {
        leftToRight.setOnClickListener {
            leftToRight()
        }

        rightToLeft.setOnClickListener {
            rightToLeft()
        }
    }

    override fun initData() {
    }

    private fun leftToRight() {
        colorTrackView.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT)
        Utils.floatValueAnimator(0f, 1f, 2000, ValueAnimator.AnimatorUpdateListener {
            val value = it.animatedValue as Float
            colorTrackView.setProgress(value)
        })
    }

    private fun rightToLeft() {
        colorTrackView.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT)
        Utils.floatValueAnimator(0f, 1f, 2000, ValueAnimator.AnimatorUpdateListener {
            val value = it.animatedValue as Float
            colorTrackView.setProgress(value)
        })
    }
}