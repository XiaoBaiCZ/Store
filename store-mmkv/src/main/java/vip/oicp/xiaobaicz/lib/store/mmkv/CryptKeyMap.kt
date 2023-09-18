package vip.oicp.xiaobaicz.lib.store.mmkv

/**
 * MMKV密钥映射
 */
fun interface CryptKeyMap {

    /**
     * 密钥映射
     * @param table 表
     * @return 密钥
     */
    fun map(table: String): String

    companion object : CryptKeyMap {
        /**
         * 自定义映射
         */
        @JvmStatic
        var hook: CryptKeyMap? = null
        override fun map(table: String): String {
            return hook?.map(table) ?: table
        }
    }

}