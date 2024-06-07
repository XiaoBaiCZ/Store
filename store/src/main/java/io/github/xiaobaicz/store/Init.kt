package io.github.xiaobaicz.store

import android.content.Context
import com.google.auto.service.AutoService
import io.github.xiaobaicz.initializer.Initializer
import java.util.ServiceLoader

@AutoService(Initializer::class)
class Init : Initializer {
    override fun onInit(context: Context) {
        loadSpi<StoreInit>().forEach {
            it.onInit(context)
        }
    }

    private inline fun <reified T> loadSpi(): List<T> {
        val clazz = T::class.java
        return try {
            ServiceLoader.load(clazz, clazz.classLoader).toList()
        } catch (t: Throwable) {
            emptyList()
        }
    }
}