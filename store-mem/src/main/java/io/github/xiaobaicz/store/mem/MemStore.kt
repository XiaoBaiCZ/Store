package io.github.xiaobaicz.store.mem

import io.github.xiaobaicz.store.Store

class MemStore : Store {
    private val map: MutableMap<String, MutableMap<String, String>> = HashMap()
    private fun findTable(table: String): MutableMap<String, String> {
        if (map[table] == null)
            map[table] = HashMap()
        return map[table]!!
    }
    override fun set(table: String, key: String, value: String?) {
        val t = findTable(table)
        if (value == null) {
            t.remove(key)
            return
        }
        t[key] = value
    }

    override fun get(table: String, key: String): String? {
        val t = findTable(table)
        return t[key]
    }

    override fun clear(table: String) {
        map[table]?.clear()
    }
}