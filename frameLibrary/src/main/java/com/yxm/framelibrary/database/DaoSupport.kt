package com.yxm.framelibrary.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.collection.ArrayMap
import androidx.collection.arrayMapOf
import com.yxm.framelibrary.database.curd.QuerySupport
import java.lang.reflect.Method
import java.util.*
import kotlin.collections.ArrayList


/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/31 14:28
 * @description
 */
class DaoSupport<T> : IDaoSupport<T> {
    private lateinit var mSqlSQLiteDatabase: SQLiteDatabase
    private lateinit var mClazz: Class<T>
    private val mPutMethodArgs = arrayOfNulls<Any>(2)
    private var mQuerySupport: QuerySupport<T>? = null

    private var mPutMethods: ArrayMap<String, Method?> = arrayMapOf()

    override fun init(sqlSQLiteDatabase: SQLiteDatabase, clazz: Class<T>) {
        mSqlSQLiteDatabase = sqlSQLiteDatabase
        mClazz = clazz
        //创建表
        val sb = StringBuffer()
        val sql = sb.append("create table if not exists ")
                .append(mClazz.simpleName)
                .append("(id integer primary key autoincrement, ")

        //获取该类的所有属性
        val fields = mClazz.declaredFields
        fields.forEach {
            it.isAccessible = true
            val name = it.name //属性声明的字段名称 如id,name,age等
            val type = it.type.simpleName//属性的类型 如String,Int等
            //进行类型转换,append成一条sql语句
            sb.append(name).append(DaoUtils.getColumnType(type)).append(",")
        }
        sb.replace(sb.length - 1, sb.length, ")")

        //创建表
        mSqlSQLiteDatabase.execSQL(sql.toString())
    }

    /**
     * 插入数据库
     * @param data 任意对象
     */
    override fun insert(data: T): Long {
        val contentValues = contentValueByObj(data)
        return mSqlSQLiteDatabase.insert(DaoUtils.getTableName(mClazz), null, contentValues)
    }


    /**
     * 批量插入
     * 开启事务提高效率
     */
    override fun insert(datas: List<T>) {
        mSqlSQLiteDatabase.beginTransaction()
        datas.forEach {
            insert(it)
        }
        mSqlSQLiteDatabase.setTransactionSuccessful()
        mSqlSQLiteDatabase.endTransaction()
    }

    /**
     * 删除
     */
    override fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return mSqlSQLiteDatabase.delete(DaoUtils.getTableName(mClazz), whereClause, whereArgs);
    }

    /**
     * 更新
     */
    override fun update(obj: T, whereClause: String, vararg whereArgs: String): Int {
        val values: ContentValues = contentValueByObj(obj)
        return mSqlSQLiteDatabase.update(DaoUtils.getTableName(mClazz),
                values, whereClause, whereArgs)
    }

    override fun querySupport(): QuerySupport<T> {
        if (mQuerySupport == null) {
            mQuerySupport = QuerySupport(mSqlSQLiteDatabase, mClazz)
        }
        return mQuerySupport as QuerySupport<T>
    }

    //obj转成contentValues
    private fun contentValueByObj(data: T): ContentValues {
        val values = ContentValues()
        val fields = mClazz.declaredFields
        //对所有属性处理
        fields.forEach {
            try {
                //设置权限
                it.isAccessible = true
                val key = it.name
                //获取该对象上此属性的值（比如,对name属性进行get(User(name = "yxm",age = 18)),则返回的值就是yxm）
                val value = it.get(data)
                // put 第二个参数是类型  把它转换
                mPutMethodArgs[0] = key
                mPutMethodArgs[1] = value

                // 方法使用反射 ， 反射在一定程度上会影响性能
                // 源码里面  activity实例 反射  View创建反射
                // 第三方以及是源码给我们提供的是最好的学习教材   插件换肤

                val fieldTypeName = it.type.name//获取属性的类型名称
                // 还是使用反射  获取方法  put  缓存方法
                var putMethod = mPutMethods[fieldTypeName]
                if (putMethod == null) {
                    //获取contentValue类的put方法
                    putMethod = ContentValues::class.java.getDeclaredMethod("put",
                            String::class.java, value.javaClass)
                    mPutMethods[fieldTypeName] = putMethod
                }
                //此处调用method的invoke方法，相当于调用contentValues的put(key,value)方法
                putMethod.invoke(values, mPutMethodArgs)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mPutMethodArgs[0] = null
                mPutMethodArgs[1] = null
            }
        }
        return values
    }
}