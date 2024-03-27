@file:Suppress("unused")
package io.github.xiaobaicz.store.mmkv

import com.google.auto.service.AutoService
import com.tencent.mmkv.MMKV
import io.github.xiaobaicz.store.Store

@AutoService(Store::class)
class MMKVStore : Store {
    private val map: MutableMap<String, MMKV> = HashMap()
    private fun findTable(table: String): MMKV {
        if (map[table] == null)
            map[table] = MMKV.mmkvWithID(table, MMKV.SINGLE_PROCESS_MODE, CryptKeyMap.map(table))
        return map[table]!!
    }
    override fun set(table: String, key: String, value: String?) {
        val t = findTable(table)
        if (value == null) {
            t.remove(key)
            return
        }
        t.encode(key, value)
    }

    override fun get(table: String, key: String): String? {
        val t = findTable(table)
        return t.decodeString(key, null)
    }

    override fun clear(table: String) {
        map[table]?.clearAll()
    }
}