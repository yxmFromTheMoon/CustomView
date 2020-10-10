package com.yxm.baselibrary.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/9 20:30
 * @description 线性itemDecoration
 */
//android.R.attrs.listDivider
class LinearItemDecoration(context: Context, drawableResId: Int)
    : RecyclerView.ItemDecoration() {

    private var mDivider:Drawable? = null

    init {
        //获取drawable
        mDivider = ContextCompat.getDrawable(context,drawableResId)
    }

    /**
     * 每个item留出分割线位置
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position != 0) {
            outRect.top = mDivider!!.intrinsicHeight
        }
    }

    /**
     * 绘制分割线
     */
    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        //在每一个item的头部绘制
        val childCount = parent.childCount
        val rect = Rect()
        rect.left = parent.paddingLeft
        rect.right = parent.width - parent.paddingRight
        for (i in 1 until childCount) {
            rect.bottom = parent.getChildAt(i).top
            rect.top = rect.bottom - mDivider!!.intrinsicHeight
            mDivider?.bounds = rect
            mDivider?.draw(canvas)
        }
    }
}
