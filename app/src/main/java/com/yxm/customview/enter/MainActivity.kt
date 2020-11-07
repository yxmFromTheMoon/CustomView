package com.yxm.customview.enter

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.yxm.baselibrary.base.BaseActivity
import com.yxm.baselibrary.dialog.AlertDialog
import com.yxm.baselibrary.recyclerview.ItemClickListener
import com.yxm.customview.R
import com.yxm.customview.activity.*
import com.yxm.customview.showToast
import com.yxm.customview.startActivity
import com.yxm.framelibrary.BaseSkinActivity
import com.yxm.framelibrary.DefaultNavigationBarJava
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : BaseSkinActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private val mList = ArrayList<ButtonBean>()
    private lateinit var mCommonAdapter: EnterListAdapterV2
    private val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        mRecyclerView = recycler_view
//        test_dialog.setOnClickListener {
//            val dialog = AlertDialog.Builder(this)
//                    .setContentView(R.layout.dialog_test_view)
//                    .setCancelable(true)
//                    .addDefaultAnimation()
//                    .setWidthAndHeight(300, 300)
//                    .create()
//            dialog.show()
//            dialog.setText(R.id.test_tv, "Test")
//
//            dialog.setOnClickListener(R.id.test_tv, View.OnClickListener {
//                "Test".showToast()
//            })
//        }
        val a = DefaultNavigationBarJava.Builder(this, view_group)
                .setTitle("测试测试")
                .builder()
//        test_entry.setOnClickListener {
//            startActivity<TestActivity> { }
//        }
        val isAllGranted = checkPermissionAllGranted(permissions);
        if (isAllGranted) {
            //Log.e("err","所有权限已经授权！");
            return;
        }
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(this,
                permissions, 1);
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                var isAllGranted = true
                // 判断是否所有的权限都已经授予了
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        isAllGranted = false
                        break
                    }
                }
                if (isAllGranted) {
                    // 所有的权限都授予了
                    Log.e("err", "权限都授权了");
                    "权限都有".showToast()
                }
            }
            else -> {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                //容易判断错
                //MyDialog("提示", "某些权限未开启,请手动开启", 1) ;
            }
        }
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private fun checkPermissionAllGranted(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                //Log.e("err","权限"+permission+"没有授权");
                return false
            }
        }
        return true
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
        val bean14 = ButtonBean("15", "CTripLoadingView")
        val bean15 = ButtonBean("16", "CommonAdapter")
        val bean16 = ButtonBean("17", "Bezier")
        val bean17 = ButtonBean("18", "LoveLayout")
        val bean18 = ButtonBean("19", "DragRv")
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
        mList.add(bean14)
        mList.add(bean15)
        mList.add(bean16)
        mList.add(bean17)
        mList.add(bean18)

        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.justifyContent = JustifyContent.FLEX_START
        mRecyclerView.layoutManager = layoutManager
        mCommonAdapter = EnterListAdapterV2(this, mList)

        mCommonAdapter.setItemClickListener(object : ItemClickListener {
            override fun onItemClick(position: Int) {
                when (mList[position].tag) {
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
                    "15" -> {
                        startActivity<CTripLoadingViewActivity> { }
                    }
                    "16" -> {
                        startActivity<CommonAdapterActivity> { }
                    }
                    "17" -> {
                        startActivity<BezierActivity> { }
                    }
                    "18" -> {
                        startActivity<LoveActivity> { }
                    }
                    "19" -> {
                        startActivity<DragItemAnimatorActivity> {}
                    }
                }
            }
        })
        mRecyclerView.adapter = mCommonAdapter
    }

    override fun initListener() {
    }


}