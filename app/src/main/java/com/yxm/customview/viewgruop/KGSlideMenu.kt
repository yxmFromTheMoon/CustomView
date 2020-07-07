package com.yxm.customview.viewgruop

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import com.yxm.customview.R
import com.yxm.customview.utils.Utils
import java.lang.RuntimeException
import kotlin.math.abs

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/7/5 11:35
 * @description
 */
class KGSlideMenu @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyle: Int = 0)
    : HorizontalScrollView(context, attr, defStyle) {

    private lateinit var mMenuView:View
    private lateinit var mContentView:View

    private var mMenuWidth:Int = 0
    private var mGestureDetector:GestureDetector
    private var mMenuIsOpen = false
    private val mContext = context
    private var mIsIntercept = false

    private val mListener :GestureDetector.SimpleOnGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            if(mMenuIsOpen){
//                if(velocityY > 0 || velocityY < 0){
//                    return false
//                }
                //小于0往左边滑
                if(velocityX < 0){
                    closeMenu()
                    return true
                }
            }else{
//                if(velocityY > 0 || velocityY < 0){
//                    return false
//                }
                //大于0是往右边滑
                if(velocityX > 0){
                    openMenu()
                    return true
                }
            }
            return true
        }
    }

    init {
        mGestureDetector = GestureDetector(mContext,mListener)

        val ta = mContext.obtainStyledAttributes(attr, R.styleable.KGSlideMenu)
        val rightMargin = ta.getDimension(R.styleable.KGSlideMenu_rightMargin,Utils.dp2px(mContext,50f).toFloat())
        mMenuWidth = (Utils.getScreenWidth(mContext) - rightMargin).toInt()
        ta.recycle()
    }

    override fun onFinishInflate() {
        //xml文件解析完毕才会加载
        super.onFinishInflate()
        //指定宽高
        //1.内容页的宽度
        //2.菜单页的宽度
        //获取containerLayout
        val container = getChildAt(0) as LinearLayout
        if(container.childCount != 2){
            throw RuntimeException("Only can set two child view")
        }
        mMenuView = container.getChildAt(0)

        mContentView = container.getChildAt(1)

        val menuLp = mMenuView.layoutParams as ViewGroup.LayoutParams
        menuLp.width = mMenuWidth
        mMenuView.layoutParams = menuLp

        val contentLp = mContentView.layoutParams as ViewGroup.LayoutParams
        contentLp.width = Utils.getScreenWidth(mContext)
        mContentView.layoutParams = contentLp

        //初始化进来，移动到contentView
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        //初始化进来，移动到contentView
        scrollTo(mMenuWidth,0)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        mIsIntercept = false
        if(mMenuIsOpen){
            val currentX = ev!!.x
            if(currentX > mMenuWidth){
                //关闭菜单
                closeMenu()
                //不需要响应任何事件
                mIsIntercept = true

                return true
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    //手指抬起，要么关闭，要么打开
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if(mIsIntercept){
            return true
        }
        if(mGestureDetector.onTouchEvent(ev)){
            return true
        }
        if (ev?.action == MotionEvent.ACTION_UP){
            //根据滚动的距离来判断是要关闭还是打开
            if (scrollX > mMenuWidth / 2){
                //关闭
                closeMenu()
            }else{
                //打开
                openMenu()
            }
            //确保super.onTouchEvent()不会执行
            return true
        }
        return super.onTouchEvent(ev)
    }


    //4.处理缩放和透明度，不断获取当前滚动的位置
    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        //获取缩放值
        val scale = 1f * l / mMenuWidth
        //右边最小缩放0.7，最大1
        val rightScale = 0.7f + 0.3f * scale
        mContentView.scaleX = rightScale
        mContentView.scaleY = rightScale
        mContentView.pivotX = 0f
        mContentView.pivotY = (measuredHeight / 2).toFloat()

        //菜单缩放，透明度
        //透明度0.5f-1.0f
        //缩放0.7f-1.0f
        val alpha = 0.5f + (1-scale) * 0.5f
        mMenuView.alpha = alpha
        val menuScale = 0.7f + (1-scale) * 0.3f
        mMenuView.scaleX = menuScale
        mMenuView.scaleY = menuScale

        //退出按钮应该紧邻在内容界面
        mMenuView.translationX = 0.2f * l
    }

    private fun closeMenu(){
        mMenuIsOpen = false
        smoothScrollTo(mMenuWidth,0)
    }

    private fun openMenu(){
        mMenuIsOpen = true
        smoothScrollTo(0,0)
    }

}