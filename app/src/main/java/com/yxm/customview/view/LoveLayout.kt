package com.yxm.customview.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.yxm.customview.R
import com.yxm.customview.utils.LoveTypeEvaluator
import java.util.*


/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/19 14:44
 * @description 添加点赞
 */
class LoveLayout @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0)
    : RelativeLayout(context, attributeSet, defStyle) {
    private var mRandom: Random = Random()
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mDrawableWidth = 0
    private var mDrawableHeight = 0
    private var mInterpolator: Array<android.view.animation.Interpolator>
    private val mImages: IntArray = intArrayOf(R.drawable.pl_blue, R.drawable.pl_red, R.drawable.pl_yellow)

    init {
        val drawable = ContextCompat.getDrawable(context, R.drawable.pl_blue)
        mDrawableHeight = drawable!!.intrinsicHeight
        mDrawableWidth = drawable.intrinsicWidth
        mInterpolator = arrayOf(AccelerateDecelerateInterpolator(), AccelerateInterpolator(),
                DecelerateInterpolator(), LinearInterpolator())
    }

    fun addLove() {
        val imageView = ImageView(context)
        imageView.setImageResource(mImages[mRandom.nextInt(mImages.size - 1)])

        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.addRule(ALIGN_PARENT_BOTTOM)
        layoutParams.addRule(CENTER_HORIZONTAL)
        imageView.layoutParams = layoutParams
        addView(imageView)
        val animator = getAnimator(imageView)
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // 执行完毕之后移除
                removeView(imageView)
            }
        })
        animator.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)
    }

    private fun getAnimator(view: View): AnimatorSet {
        val set = AnimatorSet()
        val innerAnimator = AnimatorSet()

        val alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.3f, 1f)
        val scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0.3f, 1f)
        val scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0.3f, 1f)

        innerAnimator.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator)
        innerAnimator.duration = 350
        set.playSequentially(innerAnimator, getBezierAnimator(view))

        return set
    }

    private fun getBezierAnimator(view: View): Animator? {
        val point0 = PointF(mWidth.toFloat() / 2 - mDrawableWidth / 2, mHeight.toFloat() - mDrawableHeight)
        val point1 = getPoint(1)
        val point2 = getPoint(2)
        val point3 = PointF(mRandom.nextInt(mWidth).toFloat() - mDrawableWidth, 0f)
        val typeLoveTypeEvaluator = LoveTypeEvaluator(point1, point2)
        val bezierAnimator = ObjectAnimator.ofObject(typeLoveTypeEvaluator, point0, point3)
        bezierAnimator.interpolator = mInterpolator[mRandom.nextInt(mInterpolator.size - 1)];
        bezierAnimator.duration = 3000
        bezierAnimator.addUpdateListener {
            val point = it.animatedValue as PointF
            view.x = point.x
            view.y = point.y
            // 透明度
            val t: Float = it.animatedFraction
            view.alpha = 1 - t + 0.2f
        }
        return bezierAnimator
    }

    private fun getPoint(index: Int): PointF {
        // 1
        return PointF((mRandom.nextInt(mWidth) - mDrawableWidth).toFloat(), (mRandom.nextInt(mHeight / 2) + (index - 1) * (mHeight / 2)).toFloat())
    }
}