package com.yxm.customview.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/12 13:02
 * @description 万能recyclerview adapter
 */
abstract class CommonAdapter<T>(context: Context, layoutResId: Int, data: List<T>)
    : RecyclerView.Adapter<ViewHolder>() {

    constructor(context: Context, data: List<T>, typeSupport: MultiTypeSupport<T>) :
            this(context, -1, data) {
        mTypeSupport = typeSupport
    }

    protected var mTypeSupport: MultiTypeSupport<T>? = null
    protected val mContext = context
    protected var mLayoutId = layoutResId
    protected val mData = data
    private var mInflater: LayoutInflater
    var mItemClickListener: ItemClickListener? = null
    var mItemLongClickListener: ItemLongClickListener? = null
    protected var mEmptyViewLayoutId: Int = 0
    protected var mEmptyView: View? = null

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == mEmptyViewLayoutId) {
            if (mEmptyViewLayoutId != 0) {
                val view = mInflater.inflate(mEmptyViewLayoutId, parent, false)
                return ViewHolder(view)
            }
            mEmptyView?.let {
                return ViewHolder(it)
            }
        }
        mTypeSupport?.let {
            mLayoutId = viewType
        }
        val view = mInflater.inflate(mLayoutId, parent, false)
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        if (mData.isEmpty()) {
            return mEmptyViewLayoutId
        }
        mTypeSupport?.let {
            return it.getLayoutId(mData[position])
        }
        return super.getItemViewType(position)
    }

    fun setEmptyViewId(emptyLayoutId: Int) {
        mEmptyViewLayoutId = emptyLayoutId
    }

    fun setEmptyView(emptyView: View) {
        mEmptyView = emptyView
        if (mEmptyView == null) {
            mEmptyView = FrameLayout(emptyView.context)
            val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT)
            val lp: ViewGroup.LayoutParams = emptyView.layoutParams
            layoutParams.width = lp.width
            layoutParams.height = lp.height
            mEmptyView!!.layoutParams = lp
        }
    }

    override fun getItemCount(): Int {
        if (mData.isEmpty()) {
            return 1
        }
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        convert(holder, mData[position], position)
        holder.itemView.setOnClickListener {
            mItemClickListener?.onItemClick(position)
        }
        holder.itemView.setOnLongClickListener {
            mItemLongClickListener?.let {
                return@setOnLongClickListener it.onItemLongClick(position)
            }
            false
        }

    }

    protected abstract fun convert(holder: ViewHolder, item: T, position: Int)

}