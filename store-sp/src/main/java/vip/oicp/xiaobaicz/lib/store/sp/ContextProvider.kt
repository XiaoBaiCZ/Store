package vip.oicp.xiaobaicz.lib.store.sp

import android.content.Context

fun interface ContextProvider {
    fun get(): Context
}