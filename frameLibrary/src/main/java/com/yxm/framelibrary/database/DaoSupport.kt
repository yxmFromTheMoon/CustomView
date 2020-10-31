package com.yxm.framelibrary.database

import android.database.sqlite.SQLiteDatabase

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/31 14:28
 * @description
 */
class DaoSupport<T> : IDaoSupport<T> {
    private lateinit var mSqlSQLiteDatabase: SQLiteDatabase
    private lateinit var mClazz: Class<T>

    override fun init(sqlSQLiteDatabase: SQLiteDatabase, clazz: Class<T>) {
        mSqlSQLiteDatabase = sqlSQLiteDatabase
        mClazz = clazz
        //创建表
        val sb = StringBuffer()
        val sql = sb.append("create table if not exists")
                .append(mClazz.simpleName)
                .append("(id integer primary key autoincrement,")

        val fields = mClazz.declaredFields
        fields.forEach {
            it.isAccessible = true
            val name = it.name
            val type = it.type.simpleName
            //进行类型转换
            sb.append(name).append(type)
        }

        mSqlSQLiteDatabase.execSQL(sql.toString())
    }

    override fun insert(data: T): Int {

        return 0
    }
}