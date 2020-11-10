package com.yxm.framelibrary.pullalive

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.yxm.framelibrary.IServiceAidl
import java.lang.Exception

/**
 *@author: yxm
 *@time: 2020/11/10
 *@description:双进程守护之任务进程
 */
class MessageService : Service() {

    private lateinit var mBinder: MyBinder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Runnable {
            while (true) {
                Log.d("Service", "等待连接")
                try {
                    Thread.sleep(2000)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        startService(Intent(this, GuardService::class.java))
        bindService(Intent(this, GuardService::class.java), mServiceConnection, Context.BIND_IMPORTANT)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        mBinder = MyBinder()
        return mBinder
    }

    private val mServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val aidl = IServiceAidl.Stub.asInterface(service)
            Toast.makeText(this@MessageService, aidl.serviceName, Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            startService(Intent(this@MessageService, GuardService::class.java))
            //bindService(Intent(this@MessageService, GuardService::class.java), mServiceConnection, Context.BIND_IMPORTANT)
        }
    }

    private class MyBinder : IServiceAidl.Stub() {
        override fun getServiceName(): String {
            return MessageService::class.java.simpleName
        }
    }
}