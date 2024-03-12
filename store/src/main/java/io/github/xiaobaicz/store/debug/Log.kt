@file:JvmName("Debug")
package io.github.xiaobaicz.store.debug

var storeLog: Boolean = false

internal fun log(msg: Any) {
    if (!storeLog) return
    println("Store | $msg")
}

internal fun <T> timeLog(tag: String = "", func: ()->T): T {
    val time = System.currentTimeMillis()
    val result = func()
    log("$tag: ${System.currentTimeMillis() - time}ms")
    return result
}