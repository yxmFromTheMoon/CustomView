package com.yxm.customview.recyclerview

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/12 19:45
 * @description
 */
class GlideImageLoader(path: String) : ViewHolder.ImageLoader(path) {

    override fun loadImage(imageView: ImageView, path: String) {
        Glide.with(imageView.context).load(path).into(imageView)
    }
}