@file:Suppress("unused")
package vip.oicp.xiaobaicz.lib.store.sp

import android.content.Context
import androidx.startup.Initializer

class ContextProviderInitializer : Initializer<ContextProvider> {
    override fun create(context: Context): ContextProvider {
        SPStoreImpl.contextProvider = ContextProvider { context.applicationContext }
        return SPStoreImpl.contextProvider
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}