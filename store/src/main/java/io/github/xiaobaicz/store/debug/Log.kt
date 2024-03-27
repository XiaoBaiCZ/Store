package io.github.xiaobaicz.store.debug

import io.github.xiaobaicz.store.Store

internal fun log(msg: Any) {
    if (!Store.log) return
    println("Store | $msg")
}

internal fun <T> timeLog(tag: String = "", func: ()->T): T {
    val time = System.currentTimeMillis()
    val result = func()
    log("$tag: ${System.currentTimeMillis() - time}ms")
    return result
}