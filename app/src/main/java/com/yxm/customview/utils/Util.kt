package com.yxm.customview.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by Myron at 2020/12/26 11:45
 * @email yxmbest@163.com
 * @description
 */
class Util {
    fun toast(context: Context){
        Toast.makeText(context,"I am from a plugin", Toast.LENGTH_SHORT).show()
    }
}