package io.github.xiaobaicz.store

/**
 * 过滤器，用于筛选合适的实现类
 */
interface Filter {
    /**
     * 过滤
     * @param clazz 接口类型
     * @return true 通过
     */
    fun filter(clazz: Class<*>): Boolean
}