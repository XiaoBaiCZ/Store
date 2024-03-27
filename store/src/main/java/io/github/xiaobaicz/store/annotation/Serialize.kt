package io.github.xiaobaicz.store.annotation

import io.github.xiaobaicz.store.Serialize
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Serialize(
    val clazz: KClass<out Serialize>
)
