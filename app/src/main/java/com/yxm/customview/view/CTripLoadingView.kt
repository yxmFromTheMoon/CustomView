package com.yxm.customview.view

import android.animation.*
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.yxm.customview.utils.Utils

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/1 20:28
 * @description 仿携程loading view
 */
class CTripLoadingView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyle: Int = 0)
    : LinearLayout(context, attr, defStyle) {

    private var left: CircleView
    private var middle: CircleView
    private var right: CircleView
    private val ANIMATOR_DURATION: Long = 300

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        left = getCircleView()
        middle = getCircleView()
        right = getCircleView()
        left.exchangeColor(Color.RED)
        middle.exchangeColor(Color.GREEN)
        right.exchangeColor(Color.BLUE)
        addView(left)
        addView(middle)
        addView(right)

        post {
            leftEnlargeAnimator()
        }
    }

    //1.添加放大动画
    //2.添加缩小动画
    /**
     * 放大动画
     */
    private fun leftEnlargeAnimator() {
        val alphaAnimator = ObjectAnimator.ofFloat(left, "alpha", 0.5f, 0.8f)

        val scaleXAnimator = ObjectAnimator.ofFloat(left, "scaleX", 0.5f, 0.8f)

        val scaleYAnimator = ObjectAnimator.ofFloat(left, "scaleY", 0.5f, 0.8f)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator)
        animatorSet.duration = ANIMATOR_DURATION
        animatorSet.interpolator = DecelerateInterpolator()


        val alpha1Animator = ObjectAnimator.ofFloat(left, "alpha", 0.8f, 0.5f)

        val scaleX1Animator = ObjectAnimator.ofFloat(left, "scaleX", 0.8f, 0.5f)

        val scaleY1Animator = ObjectAnimator.ofFloat(left, "scaleY", 0.8f, 0.5f)


        val animatorSet1 = AnimatorSet()
        animatorSet1.playTogether(alpha1Animator, scaleX1Animator, scaleY1Animator)
        animatorSet1.duration = ANIMATOR_DURATION
        animatorSet1.interpolator = AccelerateInterpolator()

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                animatorSet1.start()
                middleEnlargeAnimator()
            }
        })

        animatorSet1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                animatorSet.start()
            }
        })

        animatorSet.start()


    }

    private fun middleEnlargeAnimator() {
        val alphaAnimator = ObjectAnimator.ofFloat(middle, "alpha", 0.5f, 0.8f)

        val scaleXAnimator = ObjectAnimator.ofFloat(middle, "scaleX", 0.5f, 0.8f)

        val scaleYAnimator = ObjectAnimator.ofFloat(middle, "scaleY", 0.5f, 0.8f)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator)
        animatorSet.duration = 200
        animatorSet.interpolator = DecelerateInterpolator()


        val alpha1Animator = ObjectAnimator.ofFloat(middle, "alpha", 0.8f, 0.5f)

        val scaleX1Animator = ObjectAnimator.ofFloat(middle, "scaleX", 0.8f, 0.5f)

        val scaleY1Animator = ObjectAnimator.ofFloat(middle, "scaleY", 0.8f, 0.5f)

        val animatorSet1 = AnimatorSet()
        animatorSet1.playTogether(alpha1Animator, scaleX1Animator, scaleY1Animator)
        animatorSet1.duration = ANIMATOR_DURATION
        animatorSet1.interpolator = AccelerateInterpolator()

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                animatorSet1.start()
                rightEnlargeAnimator()
            }
        })

        animatorSet1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                animatorSet.start()
            }
        })

        animatorSet.start()

    }

    private fun rightEnlargeAnimator() {
        val alphaAnimator = ObjectAnimator.ofFloat(right, "alpha", 0.5f, 0.8f)

        val scaleXAnimator = ObjectAnimator.ofFloat(right, "scaleX", 0.5f, 0.8f)

        val scaleYAnimator = ObjectAnimator.ofFloat(right, "scaleY", 0.5f, 0.8f)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator)
        animatorSet.duration = 100
        animatorSet.interpolator = DecelerateInterpolator()


        val alpha1Animator = ObjectAnimator.ofFloat(right, "alpha", 0.8f, 0.5f)

        val scaleX1Animator = ObjectAnimator.ofFloat(right, "scaleX", 0.8f, 0.5f)

        val scaleY1Animator = ObjectAnimator.ofFloat(right, "scaleY", 0.8f, 0.5f)


        val animatorSet1 = AnimatorSet()
        animatorSet1.playTogether(alpha1Animator, scaleX1Animator, scaleY1Animator)
        animatorSet1.duration = ANIMATOR_DURATION
        animatorSet1.interpolator = AccelerateInterpolator()

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                animatorSet1.start()
            }
        })

        animatorSet1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                animatorSet.start()
            }
        })

        animatorSet.start()

    }

    /**
     * 缩小动画
     */
    private fun leftShrinkAnimator() {
        val alphaAnimator = ObjectAnimator.ofFloat(left, "alpha", 1f, 0.5f)
        val scaleXAnimator = ObjectAnimator.ofFloat(left, "scaleX", 1f, 0.5f)
        val scaleYAnimator = ObjectAnimator.ofFloat(left, "scaleY", 1f, 0.5f)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator)
        animatorSet.duration = ANIMATOR_DURATION
        animatorSet.interpolator = AccelerateInterpolator()
        alphaAnimator.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator?) {
                middleShrinkAnimator()
            }
        })
        animatorSet.start()

    }

    private fun middleShrinkAnimator() {
        val alphaAnimator = ObjectAnimator.ofFloat(middle, "alpha", 1f, 0.5f)
        val scaleXAnimator = ObjectAnimator.ofFloat(middle, "scaleX", 1f, 0.5f)
        val scaleYAnimator = ObjectAnimator.ofFloat(middle, "scaleY", 1f, 0.5f)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator)
        animatorSet.duration = ANIMATOR_DURATION
        animatorSet.interpolator = AccelerateInterpolator()

        alphaAnimator.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator?) {
                rightShrinkAnimator()
            }
        })
        animatorSet.start()

    }

    private fun rightShrinkAnimator() {
        val alphaAnimator = ObjectAnimator.ofFloat(right, "alpha", 1f, 0.5f)
        val scaleXAnimator = ObjectAnimator.ofFloat(right, "scaleX", 1f, 0.5f)
        val scaleYAnimator = ObjectAnimator.ofFloat(right, "scaleY", 1f, 0.5f)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator)
        animatorSet.duration = ANIMATOR_DURATION
        animatorSet.interpolator = AccelerateInterpolator()

        alphaAnimator.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator?) {
                leftEnlargeAnimator()
            }
        })
        animatorSet.start()

    }


    private fun getCircleView(): CircleView {
        val circleView = CircleView(context)
        val params = RelativeLayout.LayoutParams(Utils.dp2px(context, 15f),
                Utils.dp2px(context, 15f))
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        params.leftMargin = Utils.dp2px(context, 10f)
        circleView.layoutParams = params
        circleView.scaleX = 0.5f
        circleView.scaleY = 0.5f
        circleView.alpha = 0.5f
        return circleView
    }

}