package com.yxm.framelibrary

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/18 16:00
 * @description
 */
//如果需要无参的构造方法，需要给各个属性声明一个默认值
data class Person(var name: String = "", var age: Int = 0)