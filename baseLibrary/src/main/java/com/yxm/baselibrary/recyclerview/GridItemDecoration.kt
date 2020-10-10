package com.yxm.baselibrary.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/9 21:27
 * @description 网格itemDecoration
 */
class GridItemDecoration(context: Context, drawableResId: Int) : RecyclerView.ItemDecoration() {

    private var mDivider: Drawable? = null

    init {
        //获取drawable
        mDivider = ContextCompat.getDrawable(context, drawableResId)
    }

    /**
     * 每个item留出分割线位置
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        //留出分割线位置，下边和右边
        //最下边和最右边不需要
        var bottom = mDivider!!.intrinsicHeight
        var right = mDivider!!.intrinsicWidth
        if (isLastRow(view, parent)) {
            bottom = 0
        }
        if (isLastColumn(view, parent)) {
            right = 0
        }
        outRect.bottom = bottom
        outRect.right = right
    }

    /**
     * 获取列数
     */
    private fun getSpanCount(parent: RecyclerView): Int {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            return layoutManager.spanCount
        }
        return 1
    }

    /**
     * 是否是最后一列
     * （currentPosition + 1） % spanCount
     */
    private fun isLastColumn(view: View, parent: RecyclerView): Boolean {
        val currentPosition = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        return (currentPosition + 1) % getSpanCount(parent) == 0
    }

    /**
     * 是否是最后一行
     * (currentPosition + 1) 大于 spanCount * (行数 - 1)
     */
    private fun isLastRow(view: View, parent: RecyclerView): Boolean {
        val currentPosition = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        //列数
        val spanCount = getSpanCount(parent)

        //获取行数
        val rowNumbers = if (parent.adapter!!.itemCount % spanCount == 0)
            parent.adapter!!.itemCount / spanCount
        else
            parent.adapter!!.itemCount / spanCount + 1

        return (currentPosition + 1) > spanCount * (rowNumbers - 1)

    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        //绘制
        drawHorizontal(canvas, parent)
        drawVertical(canvas, parent)
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            val params = childView.layoutParams as RecyclerView.LayoutParams
            val left = childView.left - params.leftMargin
            val right = childView.right + mDivider!!.intrinsicWidth + params.rightMargin
            val top = childView.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight
            mDivider?.setBounds(left, top, right, bottom)
            mDivider?.draw(canvas)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            val params = childView.layoutParams as RecyclerView.LayoutParams
            val left = childView.right + params.rightMargin
            val right = left + mDivider!!.intrinsicWidth
            val top = childView.top - params.topMargin
            val bottom = childView.bottom + params.bottomMargin
            mDivider?.setBounds(left, top, right, bottom)
            mDivider?.draw(canvas)
        }
    }
}