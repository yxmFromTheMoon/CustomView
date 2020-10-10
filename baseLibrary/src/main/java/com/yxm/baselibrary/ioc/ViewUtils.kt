package com.yxm.baselibrary.ioc

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.Toast
import com.yxm.baselibrary.base.BaseActivity
import java.lang.reflect.Method


/**
 *@author: yxm
 *@time: 2020/10/10
 *@description:
 */
object ViewUtils {
    @JvmStatic
    fun inject(activity: BaseActivity) {
        inject(ViewFinder(activity), activity)
    }

    @JvmStatic
    fun inject(view: View) {
        inject(ViewFinder(view), view)
    }

    private fun inject(viewFinder: ViewFinder, any: Any) {
        injectFiled(viewFinder, any)
        injectEvent(viewFinder, any)
    }

    /**
     * 注入事件，比如点击事件，检测网络等，还可以自己添加其他注解事件
     */
    private fun injectEvent(viewFinder: ViewFinder, any: Any) {
        //1.获取类里面所有的方法
        val clazz = any.javaClass
        //获取所有属性包括私有和公有
        val methods = clazz.declaredMethods
        //2.获取viewById的里面的value值
        methods.forEach { method ->
            val onClick = method.getAnnotation(OnClick::class.java)
            if (onClick != null) {
                //获取注解里面的值，即view的Id
                val viewId = onClick.value
                //3.找到view
                viewId.forEach {
                    val view = viewFinder.findViewById(it)
                    // 扩展功能 检测网络
                    val isCheckNet = method.getAnnotation(CheckNet::class.java) != null
                    view?.setOnClickListener(DeclaredOnClickListener(method, any, isCheckNet))
                }
            }
        }
    }

    private class DeclaredOnClickListener(method: Method, any: Any, isCheckNet: Boolean) : View.OnClickListener {

        private val mMethod = method
        private val mAny = any
        private val mIsCheckNet = isCheckNet

        override fun onClick(v: View?) {
            if (mIsCheckNet) {
                if (!isNetworkAvailable(v!!.context)) {
                    Toast.makeText(v.context, "请检查网络", Toast.LENGTH_SHORT).show()
                }
            }
            //点击事件
            try {
                mMethod.isAccessible = true
                //反射执行方法
                mMethod.invoke(mAny, v)
            } catch (e: Exception) {
                e.printStackTrace()
                //解决未添加注解,xml中声明了onClick属性的情况
                try {
                    mMethod.invoke(mAny, null)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    /**
     * 注入属性
     */
    private fun injectFiled(viewFinder: ViewFinder, any: Any) {
        //1.获取类里面所有的属性
        val clazz = any.javaClass
        //获取所有属性包括私有和公有
        val fields = clazz.declaredFields
        //2.获取viewById的里面的value值
        fields.forEach { field ->
            val viewById = field.getAnnotation(ViewById::class.java)
            if (viewById != null) {
                //获取注解里面的值，即view的Id
                val viewId = viewById.value
                //3.找到view
                val view = viewFinder.findViewById(viewId)
                view?.let {
                    //能够注入所有修饰符
                    field.isAccessible = true
                    //4.动态注入找到的view
                    try {
                        field.set(any, view)
                    } catch (exception: IllegalAccessException) {
                        exception.printStackTrace()
                    }
                }
            }
        }
    }

    /**
     * 判断当前网络是否可用
     */
    private fun isNetworkAvailable(context: Context): Boolean {
        // 得到连接管理器对象
        try {
            val connectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager
                    .activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}