package com.yxm.customview.task

import com.yxm.baselibrary.startup.Task
import com.yxm.framelibrary.skin.SkinPreUtils

/**
 * Created by Myron at 2021/2/27 15:50
 * @email yxmbest@163.com
 * @description
 */
class SkinPreUtilsTask : Task() {

    override fun run() {
        SkinPreUtils.init(mContext)
    }
}