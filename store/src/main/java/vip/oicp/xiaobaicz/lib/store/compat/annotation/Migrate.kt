package vip.oicp.xiaobaicz.lib.store.compat.annotation

/**
 * 迁移，通过[vip.oicp.xiaobaicz.lib.store.compat.GetterHook]进行迁移旧数据
 * @param key 旧Key
 * @param table 旧表
 */
@Target(AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Migrate(
    val key: String,
    val table: String = TABLE_DEFAULT,
)

const val TABLE_DEFAULT = "default"
