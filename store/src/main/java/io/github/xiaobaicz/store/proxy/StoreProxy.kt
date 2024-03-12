package io.github.xiaobaicz.store.proxy

import io.github.xiaobaicz.store.Serialize
import io.github.xiaobaicz.store.Store
import io.github.xiaobaicz.store.Clear
import io.github.xiaobaicz.store.compat.annotation.Migrate
import io.github.xiaobaicz.store.compat.storeGetterHook
import io.github.xiaobaicz.store.debug.log
import io.github.xiaobaicz.store.debug.timeLog
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * 存储代理类
 * @param clazz 目标接口类型
 * @param store 存储接口
 * @param serialize 序列化接口
 */
internal class StoreProxy(
    private val clazz: Class<*>,
    private val store: Store,
    private val serialize: Serialize,
) : InvocationHandler {

    override fun hashCode(): Int = clazz.hashCode() + store.hashCode() + serialize.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is StoreProxy) return other == this
        return clazz == other.clazz && store == other.store && serialize == other.serialize
    }

    override fun toString(): String {
        return "Proxy<${clazz.name} | ${store::class.qualifiedName} | ${serialize::class.qualifiedName}>"
    }

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        method ?: throw NullPointerException("There is no method.")
        return when (method.declaringClass) {
            clazz -> handle(method, args)
            Clear::class.java -> store.clear(table)
            Any::class.java -> method.invoke(this, *(args ?: arrayOf()))
            else -> throw RuntimeException("Methods that are not declared classes")
        }
    }

    // 是否Setter方法
    private val Method.isSetter: Boolean get() = parameterTypes.size == 1
            && returnType == Void.TYPE
            && name.startsWith("set")

    // 是否Getter方法
    private val Method.isGetter: Boolean get() = parameterTypes.isEmpty()
            && returnType != Void.TYPE
            && (name.startsWith("get") || name.startsWith("is"))

    // 分组名字，由接口类型计算
    private val table: String = clazz.name

    // 键名字，由方法名计算
    private fun key(method: Method): String = when {
        method.name.startsWith("set") -> method.name.substring(3)
        method.name.startsWith("get") -> method.name.substring(3)
        method.name.startsWith("is") -> method.name.substring(2)
        else -> throw RuntimeException("Not a Getter or Setter method, can't get Key")
    }

    // 处理接口方法
    private fun handle(method: Method, args: Array<out Any>?): Any? = timeLog(" - time") {
        when {
            method.isGetter -> {
                val any = getter(method.returnType, table, key(method))
                any ?: migrate(method.returnType, table, key(method), method)
            }
            method.isSetter -> setter(table, key(method), args?.get(0))
            else -> throw RuntimeException("Not a Getter or Setter method")
        }
    }

    // 处理Getter
    private fun getter(returnType: Class<*>, table: String, key: String): Any? {
        // 获取&反序列化
        log("getter: $table -> $key")
        val data = store.get(table, key)
        log(" - get: $data")
        val any = serialize.deserialize(returnType, data)
        log(" - deserialize: $any")
        return any
    }

    // 处理Setter
    private fun setter(table: String, key: String, arg: Any?) {
        // 序列化&存储
        log("setter: $table -> $key")
        log(" - serialize: $arg")
        val data = serialize.serialize(arg)
        log(" - set: $data")
        store.set(table, key, data)
    }

    private fun migrate(returnType: Class<*>, table: String, key: String, method: Method): Any? {
        val hook = storeGetterHook ?: return null
        val migrate = method.getAnnotation(Migrate::class.java) ?: return null
        log("migrate: ${migrate.table} -> ${migrate.key} | start")
        val old: Any? = hook.find(method.returnType, migrate.table, migrate.key)
        log(" - get: $old")
        if (old != null) {
            log(" - set: $old")
            setter(this.table, key(method), old)
        }
        log("migrate: ${migrate.table} -> ${migrate.key} | end")
        if (old == null) return null
        return getter(returnType, table, key)
    }

}