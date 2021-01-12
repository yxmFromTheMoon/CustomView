package com.yxm.customview.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.RelativeLayout
import com.yxm.customview.utils.Utils

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/8/30 15:40
 * @description 花束直播loading view
 */
class BouquetLoadingView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyle: Int = 0)
    : RelativeLayout(context, attr, defStyle) {

    private var left: CircleView
    private var middle: CircleView
    private var right: CircleView
    private val mTranslationDistanceX = Utils.dp2px(context, 20f)
    private val ANIMATOR_DURATION: Long = 400
    private var isStopAnimator = false

    init {
        left = getCircleView()
        left.exchangeColor(Color.GREEN)
        middle = getCircleView()
        middle.exchangeColor(Color.RED)
        right = getCircleView()
        right.exchangeColor(Color.BLUE)
        addView(left)
        addView(right)
        addView(middle)
        post {
            //让布局实例化之后再去开启动画
            expandAnimator()
        }
    }

    private fun expandAnimator() {
        if (isStopAnimator) {
            return
        }
        val leftTranslation = ObjectAnimator.ofFloat(left, "translationX",
                0f, -mTranslationDistanceX.toFloat())
        val rightTranslation = ObjectAnimator.ofFloat(right, "translationX",
                0f, mTranslationDistanceX.toFloat())
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(leftTranslation, rightTranslation)
        animatorSet.duration = ANIMATOR_DURATION
        animatorSet.interpolator = DecelerateInterpolator()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                //往里外面跑
                val leftColor = left.getColor()
                val middleColor = middle.getColor()
                val rightColor = right.getColor()
                middle.exchangeColor(leftColor)
                right.exchangeColor(middleColor)
                left.exchangeColor(rightColor)
                innerAnimation()
            }

        })
        animatorSet.start()

    }

    private fun innerAnimation() {
        if (isStopAnimator) {
            return
        }
        val leftTranslation = ObjectAnimator.ofFloat(left, "translationX",
                -mTranslationDistanceX.toFloat(), 0f)
        val rightTranslation = ObjectAnimator.ofFloat(right, "translationX",
                mTranslationDistanceX.toFloat(), 0f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(leftTranslation, rightTranslation)
        animatorSet.duration = ANIMATOR_DURATION
        animatorSet.interpolator = AccelerateInterpolator()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                //往里外面跑
                val leftColor = left.getColor()
                val middleColor = middle.getColor()
                val rightColor = right.getColor()
                middle.exchangeColor(leftColor)
                right.exchangeColor(middleColor)
                left.exchangeColor(rightColor)
                expandAnimator()
            }

        })
        animatorSet.start()
    }

    private fun getCircleView(): CircleView {
        val circleView = CircleView(context)
        val params = LayoutParams(Utils.dp2px(context, 8f),
                Utils.dp2px(context, 8f))
        params.addRule(CENTER_IN_PARENT)
        circleView.layoutParams = params
        return circleView
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(View.GONE)
        left.clearAnimation()
        right.clearAnimation()
        isStopAnimator = true
    }

    override fun onDetachedFromWindow() {
        clearAnimation()
        super.onDetachedFromWindow()
    }

}