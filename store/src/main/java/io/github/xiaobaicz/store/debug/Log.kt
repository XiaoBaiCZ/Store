package io.github.xiaobaicz.store.debug

import io.github.xiaobaicz.store.Store

var logTag = "Store"

internal fun log(msg: Any, tag: String = logTag) {
    if (!Store.log) return
    println("$tag | $msg")
}

internal fun <T> timeLog(label: String = "", tag: String = logTag, func: ()->T): T {
    val time = System.currentTimeMillis()
    val result = func()
    log("$label: ${System.currentTimeMillis() - time}ms", tag)
    return result
}