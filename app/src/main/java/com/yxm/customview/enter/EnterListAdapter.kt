package com.yxm.customview.enter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.yxm.customview.R

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/26 23:11
 * @Description
 */
class EnterListAdapter(data: MutableList<ButtonBean>) : RecyclerView.Adapter<EnterListAdapter.MyViewHolder>() {

    private val mData = data
    private lateinit var mListener: OnClickListener

    fun setListener(listener: OnClickListener) {
        mListener = listener
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.button.text = mData[position].text
        holder.button.tag = mData[position].tag
        holder.button.setOnClickListener {
            mListener.onClick(mData[position].tag, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val button = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_enter, parent, false)
        return MyViewHolder(button)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val button: Button = itemView.findViewById(R.id.enter_button)
    }

    interface OnClickListener {
        fun onClick(tag: String, position: Int)
    }
}