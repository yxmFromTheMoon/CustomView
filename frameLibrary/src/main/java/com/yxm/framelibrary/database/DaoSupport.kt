package com.yxm.framelibrary.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.collection.ArrayMap
import androidx.collection.arrayMapOf
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

    private var mPutMethods: ArrayMap<String, Method?> = arrayMapOf()

    override fun init(sqlSQLiteDatabase: SQLiteDatabase, clazz: Class<T>) {
        mSqlSQLiteDatabase = sqlSQLiteDatabase
        mClazz = clazz
        //创建表
        val sb = StringBuffer()
        val sql = sb.append("create table if not exists")
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
        return mSqlSQLiteDatabase.insert(mClazz.simpleName, null, contentValues)
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

    /**
     * 查询所有
     */
    override fun query(): List<T> {
        val cursor: Cursor = mSqlSQLiteDatabase.query(DaoUtils.getTableName(mClazz),
                null, null, null, null, null, null)
        return cursorToList(cursor)
    }

    /**
     * 按条件查询，后续还可以增加多个重载方法
     * todo
     */
    override fun query(columns: Array<String>, whereClause: String, whereArgs: Array<String>): List<T> {
        val cursor = mSqlSQLiteDatabase.query(mClazz.simpleName, columns,
                whereClause, whereArgs, null, null, null, null)
        return cursorToList(cursor)
    }

    private fun cursorToList(cursor: Cursor?): List<T> {
        val list = ArrayList<T>()
        if (cursor != null && cursor.moveToFirst()) {
            // 不断的从游标里面获取数据
            do {
                try {
                    val instance = mClazz.newInstance()
                    val fields = mClazz.declaredFields
                    for (field in fields) {
                        // 遍历属性
                        field.isAccessible = true
                        val name = field.name
                        // 获取角标  获取在第几列
                        val index = cursor.getColumnIndex(name)
                        if (index == -1) {
                            continue
                        }
                        // 通过反射获取 游标的方法  field.getType() -> 获取的类型
                        val cursorMethod = cursorMethod(field.type)
                        if (cursorMethod != null) {
                            // 通过反射获取了 value
                            var value: Any? = cursorMethod.invoke(cursor, index) ?: continue

                            // 处理一些特殊的部分
                            if (field.type === Boolean::class.javaPrimitiveType || field.type === Boolean::class.java) {
                                if ("0" == value.toString()) {
                                    value = false
                                } else if ("1" == value.toString()) {
                                    value = true
                                }
                            } else if (field.type === Char::class.javaPrimitiveType || field.type === Char::class.java) {
                                value = (value as String)[0]
                            } else if (field.type === Date::class.java) {
                                val date = value as Long
                                if (date <= 0) {
                                    value = null
                                } else {
                                    value = Date(date)
                                }
                            }
                            // 通过反射注入
                            field.set(instance, value)
                        }
                    }
                    // 加入集合
                    list.add(instance);
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            } while (cursor.moveToNext())
        }
        return list
    }

    // 获取游标的方法
    @Throws(java.lang.Exception::class)
    private fun cursorMethod(type: Class<*>): Method? {
        val methodName = getColumnMethodName(type)
        // type String getString(index); int getInt; boolean getBoolean
        return Cursor::class.java.getMethod(methodName!!, Int::class.javaPrimitiveType)
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

    /**
     * 获取游标方法名
     */
    private fun getColumnMethodName(fieldType: Class<*>): String? {
        val typeName = if (fieldType.isPrimitive) {
            DaoUtils.capitalize(fieldType.name)
        } else {
            fieldType.simpleName
        }
        var methodName = "get$typeName"
        if ("getBoolean" == methodName) {
            methodName = "getInt"
        } else if ("getChar" == methodName || "getCharacter" == methodName) {
            methodName = "getString"
        } else if ("getDate" == methodName) {
            methodName = "getLong"
        } else if ("getInteger" == methodName) {
            methodName = "getInt"
        }
        return methodName
    }
}