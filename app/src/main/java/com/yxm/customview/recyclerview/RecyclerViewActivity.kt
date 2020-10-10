package com.yxm.customview.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxm.customview.R
import com.yxm.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_recyclerview.*

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/9 20:31
 * @description
 */
class RecyclerViewActivity : BaseActivity() {

    private lateinit var mRv: RecyclerView
    private lateinit var mData: ArrayList<String>
    private lateinit var mAdapter: MyAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_recyclerview
    }

    override fun initView() {
        mRv = recyclerview
    }

    override fun initListener() {

    }

    override fun initData() {
        mData = ArrayList()
        for (i in 'A' until  'Z' + 1) {
            mData.add(i.toString())
        }
        mAdapter = MyAdapter(this, mData)
        //mRv.layoutManager = LinearLayoutManager(this)
        mRv.layoutManager = GridLayoutManager(this,3)
        mRv.addItemDecoration(GridItemDecoration(this,R.drawable.item_grid_decoration))
        mRv.adapter = mAdapter
    }

    class MyAdapter(context: Context, data: List<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

        private val mData = data
        private val mContext = context

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_test, parent, false))
        }

        override fun getItemCount(): Int {
            return mData.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = mData[position]
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView = view.findViewById<TextView>(R.id.item_tv)
        }
    }
}