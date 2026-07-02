package com.yxm.customview.mvi

import androidx.lifecycle.ViewModel

/**
 * @author Created by yinxiangming at 2026/6/25
 */

sealed class UiIntent {
    object TextSize : UiIntent()
    object TextColor : UiIntent()
    object TextValue : UiIntent()
}

class MviViewModel : ViewModel() {

}