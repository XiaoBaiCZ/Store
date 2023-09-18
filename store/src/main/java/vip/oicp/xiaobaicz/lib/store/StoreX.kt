@file:[JvmName("StoreX") Suppress("unused")]
package vip.oicp.xiaobaicz.lib.store

import vip.oicp.xiaobaicz.lib.store.proxy.StoreProxy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Proxy
import java.util.ServiceLoader

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

private val stores: List<Store> by lazy {
    val clazz = Store::class.java
    val stores = ServiceLoader.load(clazz, clazz.classLoader).toList()
    if (stores.isEmpty())
        throw RuntimeException("There is no Store interface implementation class, please register Store via SPI.")
    stores.apply {
        log("SPI Store:")
        for ((index, store) in this.withIndex()) {
            log("   $index: ${store::class.java}")
        }
    }
}

private val serializes: List<Serialize> by lazy {
    val clazz = Serialize::class.java
    val serializes = ServiceLoader.load(clazz, clazz.classLoader).toList()
    if (serializes.isEmpty())
        throw RuntimeException("There is no Serialize interface implementation class, please register Serialize via SPI.")
    serializes.apply {
        log("SPI Serialize:")
        for ((index, serialize) in this.withIndex()) {
            log("   $index: ${serialize::class.java}")
        }
    }
}

private fun findStore(clazz: Class<*>): Store {
    if (stores.isEmpty()) throw RuntimeException("Target Store implementation class not found")
    return stores.find {
        it.filter(clazz)
    } ?: throw RuntimeException("No suitable Store implementations found")
}

private fun findSerialize(clazz: Class<*>): Serialize {
    if (serializes.isEmpty()) throw RuntimeException("Target Serialize implementation class not found")
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