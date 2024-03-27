package io.github.xiaobaicz.store.mmkv

import io.github.xiaobaicz.store.Store

/**
 * MMKV密钥映射
 */
fun interface CryptKeyMap {

    /**
     * 密钥映射
     * @param table 表
     * @return 密钥
     */
    fun map(table: String): String?

    companion object : CryptKeyMap {
        internal var hook: CryptKeyMap? = null
        override fun map(table: String): String? {
            return hook?.map(table)
        }
    }

}

/**
 * 自定义映射
 */
fun Store.Companion.mmkvCryptKey(map: CryptKeyMap) {
    CryptKeyMap.hook = map
}