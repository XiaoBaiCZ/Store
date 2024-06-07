package io.github.xiaobaicz.store.mmkv

import com.tencent.mmkv.MMKV

interface Factory {
    fun create(table: String): MMKV

    companion object : Factory {
        var instance: Factory = this
        override fun create(table: String): MMKV {
            return MMKV.mmkvWithID(table, MMKV.SINGLE_PROCESS_MODE)
        }
    }
}