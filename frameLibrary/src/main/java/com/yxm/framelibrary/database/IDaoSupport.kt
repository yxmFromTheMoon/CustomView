package com.yxm.framelibrary.database

import android.database.sqlite.SQLiteDatabase
import com.yxm.framelibrary.database.curd.QuerySupport

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/31 14:27
 * @description 数据库支持接口
 */
interface IDaoSupport<T> {

    fun insert(data: T): Long

    fun init(sqlSQLiteDatabase: SQLiteDatabase, clazz: Class<T>)

    fun insert(datas: List<T>)

    fun delete(whereClause: String, whereArgs: Array<String>): Int

    fun update(obj: T, whereClause: String, vararg whereArgs: String): Int

    //获取查询帮助类
    fun querySupport(): QuerySupport<T>
}