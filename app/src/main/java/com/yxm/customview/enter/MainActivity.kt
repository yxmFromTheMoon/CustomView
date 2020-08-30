package com.yxm.customview.enter

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxm.customview.R
import com.yxm.customview.activity.*
import com.yxm.customview.basic.BaseActivity
import com.yxm.customview.gone
import com.yxm.customview.showToast
import com.yxm.customview.startActivity
import com.yxm.customview.view.ListDataScreenView
import com.yxm.customview.view.ListMenuAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity(), EnterListAdapter.OnClickListener {

    private lateinit var mRecyclerView: RecyclerView
    private var mAdapter: EnterListAdapter? = null
    private val mList = ArrayList<ButtonBean>()

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        mRecyclerView = recycler_view
    }

    override fun initData() {
        val bean = ButtonBean("1", "textView")
        val bean1 = ButtonBean("2", "checkView")
        val bean2 = ButtonBean("3", "circleProgressView")
        val bean3 = ButtonBean("4", "58LoadingView")
        val bean4 = ButtonBean("5", "colorTrackTextView")
        val bean5 = ButtonBean("6", "pieView")
        val bean6 = ButtonBean("7", "QQStepView")
        val bean7 = ButtonBean("8", "RatingBar")
        val bean8 = ButtonBean("9", "CheckBox")
        val bean9 = ButtonBean("10", "LetterSideBar")
        val bean10 = ButtonBean("11", "KGSlideMenu")
        val bean11 = ButtonBean("12", "VerticalDragListView")
        val bean12 = ButtonBean("13", "ListDataScreenView")
        val bean13 = ButtonBean("14", "BouquetLoadingView")
        mList.add(bean)
        mList.add(bean1)
        mList.add(bean2)
        mList.add(bean3)
        mList.add(bean4)
        mList.add(bean5)
        mList.add(bean6)
        mList.add(bean7)
        mList.add(bean8)
        mList.add(bean9)
        mList.add(bean10)
        mList.add(bean11)
        mList.add(bean12)
        mList.add(bean13)
        mAdapter = EnterListAdapter(mList)
        mRecyclerView.adapter = mAdapter
    }

    override fun initListener() {
        mAdapter!!.setListener(this)
    }

    override fun onClick(tag: String, position: Int) {
        when (tag) {
            "1" -> {
                startActivity<TextViewActivity> {}
            }
            "2" -> {
                startActivity<CheckViewActivity> {}
            }
            "3" -> {
                startActivity<CircleProgressViewActivity> {}
            }
            "4" -> {
                startActivity<LoadingView58Activity> {}
            }
            "5" -> {
                startActivity<ViewPagerActivity> {}
            }
            "6" -> {
                startActivity<PieViewActivity> {}
            }
            "7" -> {
                startActivity<QQStepViewActivity> {}
            }
            "8" -> {
                startActivity<RatingBarActivity> {}
            }
            "9" -> {
                startActivity<SmoothCheckBoxActivity> {}
            }
            "10" -> {
                startActivity<LetterSideBarActivity> {}
            }
            "11" -> {
                startActivity<KGMenuActivity> { }
            }
            "12" -> {
                startActivity<VerticalDragListViewActivity> {}
            }
            "13" -> {
                startActivity<ListDataScreenActivity> { }
            }
            "14" -> {
                startActivity<BouquetLoadingViewActivity> { }
            }
        }
    }
}