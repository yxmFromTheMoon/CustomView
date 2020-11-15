package com.yxm.customview.activity

import android.content.Intent
import android.media.Image
import android.os.Debug
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import com.yxm.baselibrary.imageloader.ImageLoaderUtils
import com.yxm.baselibrary.ui.banner.BannerAdapter
import com.yxm.customview.R
import com.yxm.customview.startActivity
import com.yxm.customview.startActivityForResult
import com.yxm.framelibrary.BaseSkinActivity
import com.yxm.framelibrary.imagepicker.ImageSelector
import com.yxm.framelibrary.imagepicker.SelectPictureActivity
import kotlinx.android.synthetic.main.activity_test.*

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/21 20:33
 * @description
 */
class TestActivity : BaseSkinActivity() {

    private val mImageUrls = listOf("http://gank.io/images/cfb4028bfead41e8b6e34057364969d1",
            "https://pic.downk.cc/item/5e7b64fd504f4bcb040fae8f", "http://gank.io/images/cfb4028bfead41e8b6e34057364969d1", "http://gank.io/images/cfb4028bfead41e8b6e34057364969d1", "http://gank.io/images/cfb4028bfead41e8b6e34057364969d1")


    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }

    override fun initView() {
//        banner_view.setAdapter(object : BannerAdapter() {
//            override fun getCount(): Int {
//                return mImageUrls.size
//            }
//
//            override fun getView(position: Int, convertView: View?): View {
//                val imageView: ImageView?
//                if (convertView == null) {
//                    imageView = ImageView(this@TestActivity)
//                    imageView.scaleType = ImageView.ScaleType.FIT_XY
//                } else {
//                    imageView = convertView as ImageView
//                }
//
//                ImageLoaderUtils.displayImage(imageView, mImageUrls[position])
//                return imageView
//            }
//        }).setInterpolator(AccelerateInterpolator())
//                .start()

    }

    override fun initListener() {
        val images = arrayListOf("/storage/emulated/0/DCIM/Camera/IMG_20201115_094851_1.jpg",
                "/storage/emulated/0/DCIM/Camera/IMG_20201115_094858.jpg", "/storage/emulated/0/DCIM/Camera/IMG_20201115_094854.jpg")
        test_tv.setOnClickListener {
            ImageSelector.create()
                    .max(9)
                    .multi()
                    .showCamera()
                    .originData(arrayListOf())
                    .start(this, 111)
        }

    }

    override fun initData() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 111) {
                data?.let {
                    Log.d("test_paths", it.getStringArrayListExtra(SelectPictureActivity.RESULT_LIST_KEY).toString())
                }
            }
        }
    }
}