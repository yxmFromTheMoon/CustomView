package com.yxm.customview.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
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
    private var mItemClickListener: ItemClickListener? = null
    private var mItemLongClickListener: ItemLongClickListener? = null

    fun setItemClickListener(listener: ItemClickListener){
        this.mItemClickListener = listener
    }

    fun setItemLongClickListener(listener: ItemLongClickListener){
        this.mItemLongClickListener = listener
    }

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mTypeSupport?.let {
            mLayoutId = viewType
        }
        val view = mInflater.inflate(mLayoutId, parent, false)
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        mTypeSupport?.let {
            return it.getLayoutId(mData[position])
        }
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
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