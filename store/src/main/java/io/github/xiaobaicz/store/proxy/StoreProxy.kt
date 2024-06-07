package io.github.xiaobaicz.store.proxy

import io.github.xiaobaicz.store.Clear
import io.github.xiaobaicz.store.Serialize
import io.github.xiaobaicz.store.Store
import io.github.xiaobaicz.store.exception.MethodDeclarationClassException
import io.github.xiaobaicz.store.exception.MethodMatchingException
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaGetter
import kotlin.reflect.jvm.javaSetter

/**
 * 存储代理类
 * @param clazz 目标接口类型
 * @param store 存储接口
 * @param serialize 序列化接口
 */
internal class StoreProxy(
    private val clazz: KClass<*>,
    private val store: Store,
    private val serialize: Serialize,
) : InvocationHandler {

    // 分组名字，由接口类型计算
    private val table: String = clazz.qualifiedName ?: ""

    // Java方法->访问器 映射
    private val accessorMap = HashMap<Method, KProperty.Accessor<*>>().apply {
        clazz.declaredMemberProperties.forEach {
            if (it is KMutableProperty<*>) {
                this[it.javaGetter!!] = it.getter
                this[it.javaSetter!!] = it.setter
            }
        }
    }

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        method ?: return null
        val argList = args ?: arrayOf()
        return when (method.declaringClass.kotlin) {
            clazz -> handle(method, argList)
            Clear::class -> store.clear(table)
            Any::class -> method.invoke(this, *argList)
            else -> MethodDeclarationClassException(clazz, method)
        }
    }

    // 处理接口方法
    private fun handle(method: Method, args: Array<out Any>): Any? {
        val accessor = accessorMap[method] ?: throw MethodMatchingException(method)
        val key = accessor.property.name
        return if (accessor is KProperty.Getter<*>) {
            getter(method.returnType, key)
        } else {
            setter(key, args[0])
        }
    }

    // 处理Getter
    private fun getter(returnType: Class<*>, key: String): Any? {
        // 获取&反序列化
        val data = store.get(table, key)
        val any = serialize.deserialize(returnType, data)
        return any
    }

    // 处理Setter
    private fun setter(key: String, arg: Any?) {
        // 序列化&存储
        val data = serialize.serialize(arg)
        store.set(table, key, data)
    }

    override fun toString(): String {
        return "Proxy<${clazz.qualifiedName} | ${store::class.qualifiedName} | ${serialize::class.qualifiedName}>"
    }

}