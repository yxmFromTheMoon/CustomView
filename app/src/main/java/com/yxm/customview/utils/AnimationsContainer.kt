package com.yxm.customview.utils

import android.widget.ImageView
import com.yxm.customview.R

/**
 * Created by yxm on 2022/11/29 1:48 下午
 * @email: yinxiangming@lightinthebox.com
 * @description: 帧动画加载优化，避免oom,具体实现
 * {@link FramesSequenceAnimation}
 */
object AnimationsContainer {

    private const val FPS = 30

    private val progressAnimFrames = intArrayOf(
        R.drawable.pop1,
        R.drawable.pop2,
        R.drawable.pop3,
        R.drawable.pop4,
        R.drawable.pop5,
    )

    fun createProgressDialogAnim(imageView: ImageView): FramesSequenceAnimation =
        FramesSequenceAnimation(
            imageView,
            progressAnimFrames, FPS
        )
}