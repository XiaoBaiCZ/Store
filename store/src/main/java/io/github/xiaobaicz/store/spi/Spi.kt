package io.github.xiaobaicz.store.spi

import java.util.ServiceLoader

inline fun <reified T> loadSpi(): List<T> {
    return try {
        val clazz = T::class.java
        ServiceLoader.load(clazz, clazz.classLoader).toList()
    } catch (t: Throwable) {
        emptyList()
    }
}

inline fun <reified T> lazyLoadSpi(noinline block: ((List<T>) -> Unit)? = null): Lazy<List<T>> {
    return lazy {
        loadSpi<T>().apply {
            block?.invoke(this)
        }
    }
}
