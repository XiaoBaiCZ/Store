@file:[JvmName("StoreX") Suppress("unused")]
package io.github.xiaobaicz.store

import io.github.xiaobaicz.store.debug.log
import io.github.xiaobaicz.store.debug.timeLog
import io.github.xiaobaicz.store.proxy.StoreProxy
import io.github.xiaobaicz.store.spi.lazyLoadSpi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Proxy

private val storeCache: MutableSet<Any> = HashSet()

@Suppress("UNCHECKED_CAST")
fun <T> store(clazz: Class<T>): T = timeLog(" - time") {
    if (!clazz.isInterface) throw RuntimeException("not an interface class")
    val store = findStore(clazz)
    val serialize = findSerialize(clazz)
    val target = StoreProxy(clazz, store, serialize)
    val cache = storeCache.find { it == target }
    if (cache != null) return@timeLog (cache as T).apply {
        log("get cache proxy instance: $cache")
    }
    val loader = clazz.classLoader
    Proxy.newProxyInstance(loader, arrayOf(clazz), StoreProxy(clazz, store, serialize)).apply {
        log("new proxy instance: $this")
        storeCache.add(this)
    } as T
}

/**
 * 获取Store
 */
inline fun <reified T> store(): T {
    return store(T::class.java)
}

private val stores: List<Store> by lazyLoadSpi {
    if (it.isEmpty())
        throw RuntimeException("There is no Store interface implementation class, please register Store via SPI.")
    log("SPI Store:")
    it.forEachIndexed { index, store ->
        log("   $index: ${store::class.java}")
    }
}

private val serializes: List<Serialize> by lazyLoadSpi {
    if (it.isEmpty())
        throw RuntimeException("There is no Serialize interface implementation class, please register Serialize via SPI.")
    log("SPI Serialize:")
    it.forEachIndexed { index, serialize ->
        log("   $index: ${serialize::class.java}")
    }
}

private fun findStore(clazz: Class<*>): Store {
    return stores.find {
        it.filter(clazz)
    } ?: throw RuntimeException("No suitable Store implementations found")
}

private fun findSerialize(clazz: Class<*>): Serialize {
    return serializes.find {
        it.filter(clazz)
    } ?: throw RuntimeException("No suitable Serialize implementations found")
}

/**
 * 开启协程
 */
suspend fun <T: CoApi, R> T.with(func: T.()->R?): R? = withContext(Dispatchers.IO) {
    func()
}