package com.yxm.baselibrary

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Created by Myron at 2020/11/12 8:01
 * @email yxmbest@163.com
 * @description
 */
open class SimpleLifeCycleCallbacks:Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}