package io.github.xiaobaicz.store.exception

import io.github.xiaobaicz.store.Clear
import java.lang.reflect.Method
import kotlin.reflect.KClass

class MethodDeclarationClassException(
    kClass: KClass<*>,
    method: Method
) : RuntimeException(
    "$method declaration class not $kClass / ${Clear::class} / ${Any::class}"
)