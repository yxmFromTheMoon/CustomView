package com.yxm.customview.mvi

/**
 * @author Created by yinxiangming at 2026/6/25
 * UI State
 */
sealed class UiState {
    object SUCCESS : UiState()
    object FAIL : UiState()
}