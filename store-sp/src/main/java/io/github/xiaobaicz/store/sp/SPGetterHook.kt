@file:Suppress("unused")

package io.github.xiaobaicz.store.sp

import android.content.SharedPreferences
import io.github.xiaobaicz.store.compat.GetterHook

class SPGetterHook private constructor(private val kv: SharedPreferences) : GetterHook {

    companion object {
        @JvmStatic
        fun create(kv: SharedPreferences): GetterHook = SPGetterHook(kv)
    }

    override fun find(returnType: Class<*>, table: String, key: String): Any? {
        return when {
            isInt(returnType) -> kv.getInt(key, 0)
            isLong(returnType) -> kv.getLong(key, 0L)
            isFloat(returnType) -> kv.getFloat(key, 0f)
            isBool(returnType) -> kv.getBoolean(key, false)
            isString(returnType) -> kv.getString(key, null)
            isStringSet(returnType) -> tryFunc { kv.getStringSet(key, mutableSetOf()) }
            else -> throw IllegalArgumentException("Return type is not supported")
        }
    }

    private fun isInt(type: Class<*>): Boolean {
        return type == Int::class.java || type == java.lang.Integer::class.java
    }

    private fun isLong(type: Class<*>): Boolean {
        return type == Long::class.java || type == java.lang.Long::class.java
    }

    private fun isFloat(type: Class<*>): Boolean {
        return type == Float::class.java || type == java.lang.Float::class.java
    }

    private fun isBool(type: Class<*>): Boolean {
        return type == Boolean::class.java || type == java.lang.Boolean::class.java
    }

    private fun isString(type: Class<*>): Boolean {
        return type == String::class.java || type == java.lang.String::class.java
    }

    private fun isStringSet(type: Class<*>): Boolean {
        return Set::class.java.isAssignableFrom(type)
    }

    private fun tryFunc(func: () -> Any?): Any? = try {
        func()
    } catch (t: Throwable) {
        null
    }

}
