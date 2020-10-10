package com.yxm.baselibrary.ioc

import android.app.Activity
import android.view.View

/**
 *@author: yxm
 *@time: 2020/10/10
 *@description:View的findViewById的辅助类
 */
class ViewFinder {
    private var mActivity: Activity? = null
    private var mView: View? = null

    constructor(activity: Activity?) {
        mActivity = activity
    }

    constructor(view: View?) {
        mView = view
    }

    fun findViewById(viewId: Int): View? {
        return if (mActivity != null) mActivity!!.findViewById(viewId) else mView!!.findViewById(viewId)
    }
}
