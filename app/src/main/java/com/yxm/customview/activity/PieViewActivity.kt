package com.yxm.customview.activity

import com.yxm.customview.R
import com.yxm.baselibrary.base.BaseActivity
import com.yxm.customview.data.PieData
import com.yxm.customview.view.PieView
import com.yxm.framelibrary.BaseSkinActivity
import kotlinx.android.synthetic.main.activity_pie_view.*

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/27 13:06
 * @Description
 */
class PieViewActivity : BaseSkinActivity(){

    private lateinit var pieView:PieView

    override fun getLayoutId(): Int {
        return R.layout.activity_pie_view
    }

    override fun initView() {
        pieView = pie_view
    }

    override fun initListener() {

    }

    override fun initData() {
        val pieData = PieData("动漫",20f)
        val pieData1 = PieData("电影",10f)
        val pieData2 = PieData("游戏",30f)
        val pieData3 = PieData("音乐",17f)
        val pieData4 = PieData("艺术",23f)
        val data =  ArrayList<PieData>()
        data.add(pieData)
        data.add(pieData1)
        data.add(pieData2)
        data.add(pieData3)
        data.add(pieData4)
        pieView.setData(data)
    }
}