package com.yxm.customview.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import com.yxm.customview.R
import com.yxm.customview.utils.Utils

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/8/23 20:37
 * @description
 */
class LoadingView
@JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null,
                          defStyle: Int = 0) : LinearLayout(context, attributeSet, defStyle) {

    private var mTranslateDistance: Float = 0f
    private val ANIMATOR_TIME: Long = 400
    private var mShapeView: ShapeView
    private var mShadowView: View
    private var isStopAnimator = false

    init {
        View.inflate(context, R.layout.loading_view, this)
        mShapeView = findViewById(R.id.shape_view)
        mShadowView = findViewById(R.id.shadow_bg)
        mTranslateDistance = Utils.dp2px(context, 80f).toFloat()
        //onResume方法中才开始执行
        post {
            startFallAnimator()
        }
    }

    /**
     * 开始下落动画
     */
    @SuppressLint("ObjectAnimatorBinding")
    private fun startFallAnimator() {
        if (isStopAnimator) {
            return
        }
        val animatorTranslation = ObjectAnimator.ofFloat(mShapeView, "translationY", 0f, mTranslateDistance)

        val animatorShadow = ObjectAnimator.ofFloat(mShadowView, "scaleX", 0.3f, 1f)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animatorTranslation, animatorShadow)
        animatorSet.duration = ANIMATOR_TIME
        animatorSet.interpolator = AccelerateInterpolator()

        animatorSet.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                mShapeView.exchange()
                startUpAnimator()
            }

        })

        animatorSet.start()
    }

    /**
     * 开始上抛动画
     */
    @SuppressLint("ObjectAnimatorBinding")
    private fun startUpAnimator() {
        if (isStopAnimator) {
            return
        }
        val animatorTranslation = ObjectAnimator.ofFloat(mShapeView, "translationY", mTranslateDistance, 0f)
        val animatorShadow = ObjectAnimator.ofFloat(mShadowView, "scaleX", 1f, 0.3f)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animatorTranslation, animatorShadow)
        animatorSet.duration = ANIMATOR_TIME
        animatorSet.interpolator = DecelerateInterpolator()

        animatorSet.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                setRotationAnimator()
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                startFallAnimator()
            }

        })

        animatorSet.start()
    }

    /**
     * 开始旋转动画
     */
    private fun setRotationAnimator() {
        when (mShapeView.currentShape) {
            ShapeView.Shape.Circle, ShapeView.Shape.Square -> {
                mShapeView.rotation
                val rotationAnimator = ObjectAnimator.ofFloat(mShapeView, "rotation", 0f, 180f)
                rotationAnimator.duration = ANIMATOR_TIME
                rotationAnimator.start()
            }
            ShapeView.Shape.Triangle -> {
                val rotationAnimator = ObjectAnimator.ofFloat(mShapeView, "rotation", 0f, 120f)
                rotationAnimator.duration = ANIMATOR_TIME
                rotationAnimator.start()
            }
            else -> {
            }
        }
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(View.INVISIBLE)
        mShapeView.clearAnimation()
        mShadowView.clearAnimation()
        if (parent != null) {
            (parent as ViewGroup).removeView(this)
            removeAllViews()
        }
        isStopAnimator = true
    }


}