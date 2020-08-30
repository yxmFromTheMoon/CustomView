package com.yxm.customview.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.yxm.customview.basic.BaseMenuAdapter
import com.yxm.customview.gone
import com.yxm.customview.visible

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/8/30 8:21
 * @description
 */
class ListDataScreenView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0)
    : LinearLayout(context, attributeSet, defStyle) {
    //1.创建头部用来存放tab
    private lateinit var mMenuTabView: LinearLayout

    //2创建FrameLayout用来存放内容阴影 + 内容布局
    private lateinit var mMenuMiddleView: FrameLayout
    private lateinit var mMenuContainerView: FrameLayout
    private lateinit var mShadowView: View
    private lateinit var mAdapter: BaseMenuAdapter
    private val mContext: Context = context
    private var mMenuContainerHeight = 0
    private val ANIMATOR_DURATION = 300

    //没打开为-1
    private var mCurrentPosition = -1
    private var isAnimatorExecute = false

    init {
        initLayout()
    }

    private fun initLayout() {
        orientation = VERTICAL
        //1.创建头部用来存放tab
        mMenuTabView = LinearLayout(mContext)
        mMenuTabView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(mMenuTabView)

        //2创建FrameLayout用来存放内容阴影 + 内容布局
        mMenuMiddleView = FrameLayout(mContext)
        val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
        params.weight = 1f
        mMenuMiddleView.layoutParams = params
        addView(mMenuMiddleView)

        mShadowView = View(mContext)
        mShadowView.alpha = 1f
        mShadowView.gone()
        mShadowView.setOnClickListener {
            closeMenu()
        }
        mShadowView.setBackgroundColor(Color.parseColor("#88999999"))
        mMenuMiddleView.addView(mShadowView)
        //创建菜单内容布局 存放菜单内容
        mMenuContainerView = FrameLayout(mContext)
        mMenuContainerView.setBackgroundColor(Color.WHITE)
        mMenuMiddleView.addView(mMenuContainerView)
    }

    /**
     * 设置adapter
     */
    fun setAdapter(adapter: BaseMenuAdapter) {
        mAdapter = adapter
        //获取有多少条
        val count = mAdapter.getCount()
        for (i in 0 until count) {
            //获取tab
            val tabView = mAdapter.getTabView(i, mMenuTabView)
            mMenuTabView.addView(tabView)
            val params = tabView.layoutParams as LayoutParams
            params.weight = 1f
            tabView.layoutParams = params
            //设置点击事件
            setTabClick(tabView, i)
            //获取内容
            val menuView = mAdapter.getMenuView(i, mMenuContainerView)
            menuView.gone()
            mMenuContainerView.addView(menuView)
            val menuParams = menuView.layoutParams as FrameLayout.LayoutParams
            menuParams.gravity = Gravity.CENTER
            menuView.layoutParams = menuParams
        }

        //初始时不显示数据
        //动画执行时不响应动画事件
        //tabView的颜色变化
    }

    private fun setTabClick(tabView: View, position: Int) {
        tabView.setOnClickListener {
            if (mCurrentPosition == -1) {
                //没打开去打开
                openMenu(position, tabView)
            } else {
                if (mCurrentPosition == position) {
                    closeMenu()
                } else {
                    //切换显示内容
                    var currentMenu = mMenuContainerView.getChildAt(mCurrentPosition)
                    currentMenu.gone()
                    mAdapter.onMenuClose(mMenuTabView.getChildAt(mCurrentPosition))
                    mCurrentPosition = position
                    currentMenu = mMenuContainerView.getChildAt(mCurrentPosition)
                    currentMenu.visible()
                    mAdapter.onMenuOpen(mMenuTabView.getChildAt(mCurrentPosition))
                }
            }
        }
    }

    private fun closeMenu() {
        if (isAnimatorExecute) {
            return
        }
        val translationAnimator = ObjectAnimator.ofFloat(mMenuContainerView,
                "translationY", 0f, -mMenuContainerHeight.toFloat())
        translationAnimator.duration = ANIMATOR_DURATION.toLong()
        translationAnimator.start()
        val alphaAnimator = ObjectAnimator.ofFloat(mShadowView, "alpha",
                1f, 0f)
        alphaAnimator.duration = ANIMATOR_DURATION.toLong()
        alphaAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                mMenuContainerView.getChildAt(mCurrentPosition).gone()
                mShadowView.gone()
                mCurrentPosition = -1
                isAnimatorExecute = false
            }

            override fun onAnimationStart(animation: Animator?) {
                isAnimatorExecute = true
                mAdapter.onMenuClose(mMenuTabView.getChildAt(mCurrentPosition))
            }
        })
        alphaAnimator.start()
    }

    private fun openMenu(position: Int, tabView: View) {
        if (isAnimatorExecute) {
            return
        }
        mShadowView.visible()
        mMenuContainerView.getChildAt(position).visible()

        val translationAnimator = ObjectAnimator.ofFloat(mMenuContainerView,
                "translationY", -mMenuContainerHeight.toFloat(), 0f)
        translationAnimator.duration = ANIMATOR_DURATION.toLong()
        translationAnimator.start()

        val alphaAnimator = ObjectAnimator.ofFloat(mShadowView, "alpha",
                0f, 1f)
        alphaAnimator.duration = ANIMATOR_DURATION.toLong()

        alphaAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                isAnimatorExecute = false
                mCurrentPosition = position
            }

            override fun onAnimationStart(animation: Animator?) {
                isAnimatorExecute = true
                //把当前的tabView传到外面，改变样式颜色等
                mAdapter.onMenuOpen(tabView)
            }
        })
        alphaAnimator.start()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        if (mMenuContainerHeight == 0 && height > 0) {
            mMenuContainerHeight = (height * 0.75).toInt()
            val params = mMenuContainerView.layoutParams as FrameLayout.LayoutParams
            params.height = mMenuContainerHeight
            mMenuContainerView.layoutParams = params
            mMenuContainerView.translationY = -mMenuContainerHeight.toFloat()
        }
    }
}