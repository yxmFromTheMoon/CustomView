package com.yxm.baselibrary.base

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/31 12:42
 * @description
 */
data class BaseResponse<T>(val code: Int, val msg: String, val data: T)