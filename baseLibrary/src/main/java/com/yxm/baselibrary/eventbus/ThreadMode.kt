package com.yxm.baselibrary.eventbus

import org.greenrobot.eventbus.ThreadMode

/**
 * Created by Myron at 2020/11/22 10:02
 * @email yxmbest@163.com
 * @description
 */
enum class ThreadMode {
    POSTING, MAIN, MAIN_ORDERED, BACKGROUND, ASYNC
}