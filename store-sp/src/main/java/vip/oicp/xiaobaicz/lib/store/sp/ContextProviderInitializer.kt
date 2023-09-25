@file:Suppress("unused")
package vip.oicp.xiaobaicz.lib.store.sp

import android.content.Context
import androidx.startup.Initializer
import java.lang.ref.WeakReference

class ContextProviderInitializer : Initializer<ContextProvider> {
    override fun create(context: Context): ContextProvider {
        ContextProvider.applicationContextReference = WeakReference(context)
        return ContextProvider
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}