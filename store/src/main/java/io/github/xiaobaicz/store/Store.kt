package io.github.xiaobaicz.store

import io.github.xiaobaicz.store.spi.StoreInit
import io.github.xiaobaicz.store.spi.loadSpi

/**
 * 声明存储接口，具体存储方式由实现类决定
 */
interface Store : Filter {

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
        /**
         * 存储初始化
         */
        fun init(any: Any? = null) {
            loadSpi<StoreInit>().forEach {
                it.init(any)
            }
        }
    }

}