package io.github.xiaobaicz.store.mmkv

import com.tencent.mmkv.MMKV
import io.github.xiaobaicz.store.Store

class MMKVStore : Store {
    private val map: MutableMap<String, MMKV> = HashMap()
    private fun findTable(table: String): MMKV {
        if (map[table] == null) {
            map[table] = MMKV.mmkvWithID(
                table,
                if (multiProcess)
                    MMKV.MULTI_PROCESS_MODE
                else
                    MMKV.SINGLE_PROCESS_MODE,
                CryptKeyMap.map(table),
            )
        }
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

    companion object {
        internal var multiProcess: Boolean = false
    }
}

fun Store.Companion.mmkvMultiProcess(multiProcess: Boolean) {
    MMKVStore.multiProcess = multiProcess
}