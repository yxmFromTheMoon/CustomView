package com.yxm.customview

import kotlin.math.roundToInt

/**
 * @author yxm
 * 2020/6/16 16:33
 * @function
 */
fun percent2Alpha(percent: Int): Int {
    val f = 255 * percent / 100f
    val hexInteger = f.roundToInt()
    // 十进制转换成16进制
    var hex = Integer.toHexString(hexInteger)
    // 如果只有一位，则后面添加0
    if (hex.length < 2) {
        hex = "0$hex"
    }
    return hex.toInt(16)
}