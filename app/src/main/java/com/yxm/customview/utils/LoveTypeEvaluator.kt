package com.yxm.customview.utils

import android.animation.TypeEvaluator
import android.graphics.PointF

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/19 15:29
 * @description
 */
class LoveTypeEvaluator(p1: PointF, p2: PointF) : TypeEvaluator<PointF> {

    private var point1 = p1
    private var point2 = p2

    override fun evaluate(t: Float, point0: PointF, point3: PointF): PointF? {
        // t 是 [0,1]  开始套公式 公式有四个点 还有两个点从哪里来（构造函数中来）
        val pointF = PointF()
        pointF.x = point0.x * (1 - t) * (1 - t) * (1 - t) + 3 * point1.x * t * (1 - t) * (1 - t) + 3 * point2.x * t * t * (1 - t) + point3.x * t * t * t
        pointF.y = point0.y * (1 - t) * (1 - t) * (1 - t) + 3 * point1.y * t * (1 - t) * (1 - t) + 3 * point2.y * t * t * (1 - t) + point3.y * t * t * t
        return pointF
    }
}