package io.github.xiaobaicz.store

/**
 * 声明序列化接口，具体序列化方式由实现类决定
 */
interface Serialize {

    /**
     * 序列化
     * @param target 目标对象
     * @return 序列化字符串
     */
    fun serialize(target: Any?): String?

    /**
     * 反序列化
     * @param type 目标类型
     * @param serialize 序列化字符串
     * @return 目标对象
     */
    fun deserialize(type: Class<*>, serialize: String?): Any?

}