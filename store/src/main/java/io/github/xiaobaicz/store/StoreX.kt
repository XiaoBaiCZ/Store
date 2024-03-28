@file:JvmName("StoreX")
package io.github.xiaobaicz.store

import io.github.xiaobaicz.store.debug.log
import io.github.xiaobaicz.store.debug.timeLog
import io.github.xiaobaicz.store.proxy.StoreProxy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Proxy
import io.github.xiaobaicz.store.annotation.Serialize as SerializeAnnotation
import io.github.xiaobaicz.store.annotation.Store as StoreAnnotation

private val storeCache = HashMap<Class<*>, Any>()

/**
 * 获取Store
 */
inline fun <reified T> store(): T {
    return store(T::class.java)
}

/**
 * 获取Store
 */
@Suppress("UNCHECKED_CAST")
fun <T> store(clazz: Class<T>): T = timeLog(" - time") {
    if (!clazz.isInterface) throw IllegalArgumentException("not an interface class")
    if (storeCache[clazz] == null) {
        val store = newStore(clazz)
        val serialize = newSerialize(clazz)
        val loader = clazz.classLoader
        Proxy.newProxyInstance(loader, arrayOf(clazz), StoreProxy(clazz, store, serialize)).also {
            log("new proxy instance: $it")
            storeCache[clazz] = it
        } as T
    } else {
        (storeCache[clazz] as T).also {
            log("get cache proxy instance: $it")
        }
    }
}

private fun newStore(clazz: Class<*>): Store {
    return clazz.getAnnotation(StoreAnnotation::class.java)
        .clazz.java
        .getConstructor()
        .newInstance()
}

private fun newSerialize(clazz: Class<*>): Serialize {
    return  clazz.getAnnotation(SerializeAnnotation::class.java)
        .clazz.java
        .getConstructor()
        .newInstance()
}

/**
 * 开启协程
 */
suspend fun <T: CoApi, R> T.wait(func: T.()->R?): R? = withContext(Dispatchers.IO) {
    func()
}