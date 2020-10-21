package com.yxm.baselibrary.net

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/21 20:46
 * @description
 */
object HttpUtils {

    suspend fun getUser(name: String): User {
        return RetrofitManager.create<ApiService>()
                .getUser(name)
    }
}