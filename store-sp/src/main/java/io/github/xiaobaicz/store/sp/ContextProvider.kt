package io.github.xiaobaicz.store.sp

import android.content.Context

fun interface ContextProvider {
    fun get(): Context
    companion object {
        @JvmStatic
        var provider: ContextProvider? = null
        @JvmStatic
        val context: Context get() = provider?.get() ?: throw NullPointerException("ContextProvider is null")
    }
}