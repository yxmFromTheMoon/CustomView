package com.yxm.customview.generrictype

/**
 * Created by Myron at 2020/12/28 20:05
 * @email yxmbest@163.com
 * @description 泛型学习
 */
class GenericType {

}


class WrapperList<T>{
    private val instance:Array<Any> = arrayOf()

    fun get(index:Int): T {
        return instance[index] as T
    }
}

interface Shop<T>{
    fun buy():T

    fun refund(goods:T):Float{
        return 0f
    }

}