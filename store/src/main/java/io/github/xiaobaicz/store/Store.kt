package io.github.xiaobaicz.store

import io.github.xiaobaicz.store.compat.GetterHook

/**
 * 声明存储接口，具体存储方式由实现类决定
 */
interface Store {

    /**
     * 存储
     * @param table 分组
     * @param key 键
     * @param value 值
     */
    fun set(table: String, key: String, value: String?)

    /**
     * 获取
     * @param table 分组
     * @param key 键
     * @return 值
     */
    fun get(table: String, key: String): String?

    /**
     * 清空存储
     * @param table 分组
     */
    fun clear(table: String)

    companion object {
        var getterHook: GetterHook? = null
        var log: Boolean = false
    }

}