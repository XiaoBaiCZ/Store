package vip.oicp.xiaobaicz.lib.store.sp

import android.content.Context
import java.lang.ref.Reference

fun interface ContextProvider {
    fun get(): Context
    companion object : ContextProvider {
        internal lateinit var applicationContextReference: Reference<Context>
        override fun get(): Context = applicationContextReference.get() ?: throw NullPointerException("application context is null")
    }
}