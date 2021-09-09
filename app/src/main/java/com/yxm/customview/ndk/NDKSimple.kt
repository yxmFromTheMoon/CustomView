package com.yxm.customview.ndk

/**
 * Created by Myron at 2021/9/9 21:20
 * @email yxmbest@163.com
 * @description
 */
class NDKSimple {

    external fun  getPassword()
}

fun main() {
    val ndk = NDKSimple()
    ndk.getPassword()
}