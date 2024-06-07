package io.github.xiaobaicz.store.exception

import java.lang.reflect.Method

class MethodMatchingException(
    method: Method
) : RuntimeException(
    "$method"
)