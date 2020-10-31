package com.yxm.framelibrary.database

import android.database.sqlite.SQLiteDatabase

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/31 14:27
 * @description 数据库支持接口
 */
interface IDaoSupport<T> {

    fun insert(data: T):Int

    fun init(sqlSQLiteDatabase: SQLiteDatabase,clazz: Class<T>)
}