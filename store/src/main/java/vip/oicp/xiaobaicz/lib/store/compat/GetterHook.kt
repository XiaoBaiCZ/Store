package vip.oicp.xiaobaicz.lib.store.compat

fun interface GetterHook {
    /**
     * 查找旧值
     * @param returnType 响应类型
     * @param table 旧存储标识
     * @param key 旧存储Key
     * @return 旧值
     */
    fun find(returnType: Class<*>, table: String, key: String): Any?
}