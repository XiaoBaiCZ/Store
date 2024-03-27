package io.github.xiaobaicz.store.annotation

import io.github.xiaobaicz.store.Store
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Store(
    val clazz: KClass<out Store>
)
