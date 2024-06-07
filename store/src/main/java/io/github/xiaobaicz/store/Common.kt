@file:JvmName("StoreX")

package io.github.xiaobaicz.store

import io.github.xiaobaicz.store.proxy.StoreProxy
import io.github.xiaobaicz.store.serialize.GsonSerialize
import java.lang.reflect.Proxy
import kotlin.reflect.KClass

/**
 * 获取Store
 */
inline fun <reified T : Any> store(
    store: Store,
    serialize: Serialize = GsonSerialize
): Lazy<T> {
    return lazy { store(T::class, store, serialize) }
}

/**
 * 获取Store
 */
fun <T : Any> store(
    clazz: KClass<T>,
    store: Store,
    serialize: Serialize = GsonSerialize
): T {
    if (!clazz.java.isInterface) throw IllegalArgumentException("not an interface class")
    val javaClass = clazz.java
    @Suppress("UNCHECKED_CAST")
    return Proxy.newProxyInstance(
        javaClass.classLoader,
        arrayOf(javaClass),
        StoreProxy(clazz, store, serialize)
    ) as T
}
