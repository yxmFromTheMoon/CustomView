package com.yxm.customview.task

import com.yxm.baselibrary.imageloader.ImageLoaderUtils
import com.yxm.baselibrary.startup.MainTask
import com.yxm.baselibrary.startup.Task
import com.yxm.customview.basic.MyApplication

/**
 * Created by Myron at 2021/2/27 15:52
 * @email yxmbest@163.com
 * @description
 */
class ImageLoaderTask : Task() {

    override fun run() {
        ImageLoaderUtils.init(mContext)
    }
}