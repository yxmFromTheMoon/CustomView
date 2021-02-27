package com.yxm.baselibrary.startup

class TaskStatBean {
    private var situation: String? = null
    private var count = 0

    fun getSituation(): String? {
        return situation
    }

    fun setSituation(situation: String?) {
        this.situation = situation
    }

    fun getCount(): Int {
        return count
    }

    fun setCount(count: Int) {
        this.count = count
    }
}
