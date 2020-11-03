package com.yxm.framelibrary.database

import android.database.sqlite.SQLiteDatabase

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

    fun query(): List<T>

    fun query(columns: Array<String>, whereClause: String, whereArgs: Array<String>): List<T>
}