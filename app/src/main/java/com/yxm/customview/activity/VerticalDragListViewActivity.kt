package com.yxm.customview.activity

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxm.customview.R
import com.yxm.customview.basic.BaseActivity
import com.yxm.customview.enter.ButtonBean
import com.yxm.customview.enter.EnterListAdapter
import com.yxm.customview.showToast
import kotlinx.android.synthetic.main.activity_vertical_drag_list_view.*
import java.util.ArrayList

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/7/18 18:23
 * @description
 */
class VerticalDragListViewActivity : BaseActivity() {

    private lateinit var mRv: RecyclerView
    private var mAdapter: EnterListAdapter? = null
    private val mList = ArrayList<ButtonBean>()
    private lateinit var mTextView: TextView

    override fun getLayoutId(): Int {
        return R.layout.activity_vertical_drag_list_view
    }

    override fun initView() {
        mRv = recycler_view
        mRv.layoutManager = LinearLayoutManager(mContext)
        mTextView = text_view
    }

    override fun initListener() {
        mTextView.setOnClickListener {
            "Title".showToast()
        }
        mAdapter?.setListener(object : EnterListAdapter.OnClickListener {
            override fun onClick(tag: String, position: Int) {
                "recyclerview".showToast()
            }
        })
    }

    override fun initData() {
        val bean = ButtonBean("1", "textView")
        val bean1 = ButtonBean("2", "checkView")
        val bean2 = ButtonBean("3", "circleProgressView")
        val bean3 = ButtonBean("4", "58LoadingView")
        val bean4 = ButtonBean("5", "colorTrackTextView")
        mList.add(bean)
        mList.add(bean1)
        mList.add(bean2)
        mList.add(bean3)
        mList.add(bean4)
        mAdapter = EnterListAdapter(mList)
        mRv.adapter = mAdapter
    }
}