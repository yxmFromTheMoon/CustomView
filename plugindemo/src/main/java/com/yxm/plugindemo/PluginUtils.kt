package com.yxm.plugindemo

import android.content.Context
import android.widget.Toast

/**
 * Created by Myron at 2020/12/26 11:09
 * @email yxmbest@163.com
 * @description
 */
class PluginUtils {

    fun toast(context: Context){
        Toast.makeText(context,"I am from a plugin",Toast.LENGTH_SHORT).show()
    }
}