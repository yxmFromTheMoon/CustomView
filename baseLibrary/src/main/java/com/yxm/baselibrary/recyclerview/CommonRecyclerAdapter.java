package com.yxm.baselibrary.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/16 20:28
 * @description
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {


    protected Context mContext;
    protected LayoutInflater mInflater;
    //数据怎么办？
    protected List<T> mData;
    // 布局怎么办？
    private int mLayoutId;

    // 多布局支持
    private MultiTypeSupport<T> mMultiTypeSupport;

    public CommonRecyclerAdapter(Context context, List<T> data, int layoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData = data;
        this.mLayoutId = layoutId;
    }

    /**
     * 多布局支持
     */
    public CommonRecyclerAdapter(Context context, List<T> data, MultiTypeSupport<T> multiTypeSupport) {
        this(context, data, -1);
        this.mMultiTypeSupport = multiTypeSupport;
    }

    /**
     * 根据当前位置获取不同的viewType
     */
    @Override
    public int getItemViewType(int position) {
        // 多布局支持
        if (mMultiTypeSupport != null) {

            return mMultiTypeSupport.getLayoutId(mData.get(position));
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 多布局支持
        if (mMultiTypeSupport != null) {
            mLayoutId = viewType;
        }
        // 先inflate数据
        View itemView = mInflater.inflate(mLayoutId, parent, false);
        // 返回ViewHolder
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        // 设置点击和长按事件
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(holder,position);
                }
            });
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mLongClickListener.onItemLongClick(position);
                }
            });
        }

        // 绑定怎么办？回传出去
        convert(holder, mData.get(position),position);
    }

    /**
     * 利用抽象方法回传出去，每个不一样的Adapter去设置
     *
     * @param item 当前的数据
     */
    public abstract void convert(ViewHolder holder, T item,int position);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /***************
     * 给条目设置点击和长按事件
     *********************/
    public ItemClickListener mItemClickListener;
    public ItemLongClickListener mLongClickListener;

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setOnLongClickListener(ItemLongClickListener longClickListener) {
        this.mLongClickListener = longClickListener;
    }
}
