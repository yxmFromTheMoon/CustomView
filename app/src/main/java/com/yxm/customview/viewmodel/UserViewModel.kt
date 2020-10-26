package com.yxm.customview.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yxm.baselibrary.net.HttpUtils
import com.yxm.baselibrary.net.Repository
import com.yxm.baselibrary.net.User
import kotlinx.coroutines.launch

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/26 20:25
 * @description
 */
class UserViewModel : ViewModel() {

    private val userLiveData = MutableLiveData<String>()

    var user: User? = null

    val liveData = Transformations.switchMap(userLiveData) {
        Repository.getUserName(it)
    }

    fun getUser(name: String) {
        userLiveData.value = name
    }
}