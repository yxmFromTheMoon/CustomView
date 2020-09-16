package com.yxm.customview.recyclerview

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/13 13:32
 * @description 装饰模式为recyclerview添加头部和底部
 */
class WrapRecyclerViewAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder> = adapter

    //头部和底部的集合
    private val mHeaders: SparseArray<View> = SparseArray()
    private val mFooters: SparseArray<View> = SparseArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (mHeaders.indexOfKey(viewType) >= 0) {
            return createHeaderFooterViewHolder(mHeaders.valueAt(viewType))
        } else if (mFooters.indexOfKey(viewType) >= 0) {
            return createHeaderFooterViewHolder(mFooters.valueAt(viewType))
        }
        return mAdapter.onCreateViewHolder(parent, viewType)
    }

    private fun createHeaderFooterViewHolder(view: View): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(view) {}
    }

    override fun getItemCount(): Int {
        return mAdapter.itemCount + mHeaders.size() + mFooters.size()
    }

    override fun getItemViewType(position: Int): Int {
        //头部
        val numHeaders = mHeaders.size()
        if (position < numHeaders) {
            return mHeaders.keyAt(position)
        }

        //中间的数据
        val adapterPosition = position - numHeaders
        val adapterCount: Int = mAdapter.itemCount
        if (adapterPosition < adapterCount) {
            return mAdapter.getItemViewType(adapterPosition)
        }

        //底部
        return mAdapter.getItemViewType(adapterPosition - adapterCount)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //头部
        val numHeaders = mHeaders.size()
        if (position < numHeaders) {
            return
        }

        //中间的数据
        val adapterPosition = position - numHeaders
        val adapterCount: Int = mAdapter.itemCount
        if (adapterPosition < adapterCount) {
            return mAdapter.onBindViewHolder(holder, position)
        }
    }

    fun addHeaderView(view: View) {
        if (mHeaders.indexOfValue(view) != -1) {
            mHeaders.put(BASE_HEADER_KEY++, view)
            notifyDataSetChanged()
        }
    }

    fun removeHeaderView(view: View) {
        if (mHeaders.indexOfValue(view) >= 0) {
            mHeaders.removeAt(mHeaders.indexOfValue(view))
            notifyDataSetChanged()

        }
    }

    fun addFooterView(view: View) {
        if (mFooters.indexOfValue(view) != -1) {
            mFooters.put(BASE_FOOTER_KEY++, view)
            notifyDataSetChanged()
        }
    }

    fun removeFooterView(view: View) {
        if (mFooters.indexOfValue(view) >= 0) {
            mFooters.removeAt(mFooters.indexOfValue(view))
            notifyDataSetChanged()
        }
    }

    companion object {
        private var BASE_HEADER_KEY = 1000000
        private var BASE_FOOTER_KEY = 1000000
    }
}