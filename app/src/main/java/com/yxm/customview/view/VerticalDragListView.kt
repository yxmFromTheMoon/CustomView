package com.yxm.customview.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.customview.widget.ViewDragHelper
import java.lang.RuntimeException

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/7/18 18:11
 * @description
 */
class VerticalDragListView
@JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0)
    : FrameLayout(context, attributeSet, defStyle) {

    private var mViewDragHelper: ViewDragHelper? = null
    private lateinit var mDragListView: View
    private var mMenuViewHeight = 0

    private val mDragViewHelperCallback = object : ViewDragHelper.Callback() {
        //可拖动的view
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return mDragListView == child
        }

        //垂直拖动的距离
        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            var finalTop = top

            if (finalTop <= 0) {
                finalTop = 0;
            }
            if (finalTop >= mMenuViewHeight) {
                finalTop = mMenuViewHeight
            }
            return finalTop;
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            if (releasedChild == mDragListView) {
                if (mDragListView.top > mMenuViewHeight / 2) {
                    //滚动到菜单的高度
                    mViewDragHelper?.settleCapturedViewAt(0, mMenuViewHeight)
                } else {
                    //滚动到0的位置
                    mViewDragHelper?.settleCapturedViewAt(0, 0)
                }
            }
            invalidate()
        }
    }

    init {
        mViewDragHelper = ViewDragHelper.create(this, mDragViewHelperCallback)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val menuView = getChildAt(0)
        mMenuViewHeight = menuView.measuredHeight
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mViewDragHelper?.processTouchEvent(event!!)
        return true
    }
    private var mDownY:Float = 0f

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        //向下滑动拦截，不要给recyclerview做处理
        when(ev?.action){
            MotionEvent.ACTION_DOWN -> {
                mDownY = ev.y
                mViewDragHelper?.processTouchEvent(ev)
            }

            MotionEvent.ACTION_UP -> {
                val moveY = ev.y
                //向下滑动
                if(mDownY < moveY){
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun computeScroll() {
        if (mViewDragHelper!!.continueSettling(true)) {
            invalidate()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mDragListView = getChildAt(1)
        val childCount = childCount
        if (childCount != 2) {
            throw RuntimeException("能且只能包含两个子view")
        }
    }

}