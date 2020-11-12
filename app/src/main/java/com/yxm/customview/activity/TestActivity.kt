package com.yxm.customview.activity

import android.media.Image
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import com.yxm.baselibrary.imageloader.ImageLoaderUtils
import com.yxm.baselibrary.ui.banner.BannerAdapter
import com.yxm.customview.R
import com.yxm.framelibrary.BaseSkinActivity
import kotlinx.android.synthetic.main.activity_test.*

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/21 20:33
 * @description
 */
class TestActivity : BaseSkinActivity() {

    private val mImageUrls = listOf("http://gank.io/images/cfb4028bfead41e8b6e34057364969d1",
            "https://pic.downk.cc/item/5e7b64fd504f4bcb040fae8f")


    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }

    override fun initView() {
        banner_view.setAdapter(object : BannerAdapter() {
            override fun getCount(): Int {
                return mImageUrls.size
            }

            override fun getView(position: Int, convertView: View?): View {
                val imageView: ImageView?
                if (convertView == null) {
                    imageView = ImageView(this@TestActivity)
                    imageView.scaleType = ImageView.ScaleType.FIT_XY
                } else {
                    imageView = convertView as ImageView
                }

                ImageLoaderUtils.displayImage(imageView, mImageUrls[position])
                return imageView
            }
        }).setInterpolator(AccelerateInterpolator())
                .start()

    }

    override fun initListener() {

    }

    override fun initData() {

    }

}