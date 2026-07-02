package com.yxm.customview.basic

import com.github.moduth.blockcanary.BlockCanaryContext
import com.github.moduth.blockcanary.BuildConfig

/**
 * @author Created by yinxiangming at 2026/6/25
 */
class AppBlockCanaryContext : BlockCanaryContext() {
    override fun provideBlockThreshold(): Int {
        return 500 // 卡顿阈值，默认 1000ms，可改为 500ms 更敏感
    }

    override fun displayNotification(): Boolean {
        return true // 卡顿时弹出通知
    }

    override fun provideQualifier(): String {
        return BuildConfig.VERSION_NAME // 区分版本
    }
}