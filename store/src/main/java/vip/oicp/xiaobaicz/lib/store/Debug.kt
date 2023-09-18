@file:JvmName("Debug")
package vip.oicp.xiaobaicz.lib.store

var storeDebug: Boolean = false

internal fun log(msg: Any) {
    if (!storeDebug) return
    println("Store | $msg")
}

internal fun <T> timeLog(tag: String = "", func: ()->T): T {
    val time = System.currentTimeMillis()
    val result = func()
    log("$tag: ${System.currentTimeMillis() - time}ms")
    return result
}