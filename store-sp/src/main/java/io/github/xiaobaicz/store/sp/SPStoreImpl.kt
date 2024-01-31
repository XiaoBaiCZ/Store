@file:Suppress("unused")
package io.github.xiaobaicz.store.sp

import android.content.Context
import android.content.SharedPreferences
import com.google.auto.service.AutoService
import io.github.xiaobaicz.store.Store
import io.github.xiaobaicz.store.sp.annotation.SPStore

@AutoService(Store::class)
class SPStoreImpl : Store {
    private val map: MutableMap<String, SharedPreferences> = HashMap()
    private fun findTable(table: String): SharedPreferences {
        if (map[table] == null)
            map[table] = ContextProvider.context.getSharedPreferences(table, Context.MODE_PRIVATE)
        return map[table]!!
    }
    override fun set(table: String, key: String, value: String?) {
        val editor = findTable(table).edit()
        if (value == null) {
            editor.remove(key).apply()
            return
        }
        editor.putString(key, value).apply()
    }

    override fun get(table: String, key: String): String? {
        val t = findTable(table)
        return t.getString(key, null)
    }

    override fun filter(clazz: Class<*>): Boolean {
        return clazz.getAnnotation(SPStore::class.java) != null
    }
}