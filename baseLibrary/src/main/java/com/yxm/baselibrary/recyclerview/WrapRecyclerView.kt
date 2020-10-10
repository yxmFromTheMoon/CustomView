package com.yxm.baselibrary.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/13 14:17
 * @description
 */
class WrapRecyclerView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var mAdapter: WrapRecyclerViewAdapter? = null
    private val mAdapterObserver: AdapterDataObserver = object : AdapterDataObserver() {

        override fun onChanged() {
            super.onChanged()
            mAdapter?.notifyDataSetChanged()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            mAdapter?.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            mAdapter?.notifyItemRangeChanged(positionStart, itemCount, payload)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            mAdapter?.notifyItemRangeInserted(positionStart,itemCount)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            mAdapter?.notifyItemMoved(fromPosition, toPosition)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            mAdapter?.notifyItemRangeRemoved(positionStart, itemCount)
        }
    }

    override fun setAdapter(adapter: Adapter<ViewHolder>?) {
        mAdapter = if (adapter is WrapRecyclerViewAdapter) {
            adapter
        } else {
            adapter?.registerAdapterDataObserver(mAdapterObserver)
            WrapRecyclerViewAdapter(adapter!!)
        }
        super.setAdapter(mAdapter)
    }

    fun addHeaderView(view: View) {
        mAdapter?.addHeaderView(view)
    }

    fun removeHeaderView(view: View) {
        mAdapter?.removeHeaderView(view)
    }

    fun addFooterView(view: View) {
        mAdapter?.addFooterView(view)
    }

    fun removeFooterView(view: View) {
        mAdapter?.removeFooterView(view)
    }
}