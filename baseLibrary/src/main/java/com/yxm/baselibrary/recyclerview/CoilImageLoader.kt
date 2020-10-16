package com.yxm.baselibrary.recyclerview

import android.widget.ImageView
import coil.load

/**
 *@author: yxm
 *@time: 2020/10/16
 *@description: 协程图片加载
 */
class CoilImageLoader(path: String) : ViewHolder.ImageLoader(path) {
    override fun loadImage(imageView: ImageView, path: String) {
        imageView.load(path)
    }
}