package com.yxm.framelibrary.pullalive

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder

/**
 *@author: yxm
 *@time: 2020/11/10
 *@description:双进程守护之守护进程
 */
class GuardService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private val mServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }
}