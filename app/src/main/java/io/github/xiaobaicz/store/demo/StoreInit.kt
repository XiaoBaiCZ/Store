package io.github.xiaobaicz.store.demo

import android.content.Context
import com.google.auto.service.AutoService
import io.github.xiaobaicz.initializer.Initializer
import io.github.xiaobaicz.store.Store
import io.github.xiaobaicz.store.compat.GetterHook
import io.github.xiaobaicz.store.mmkv.mmkvCryptKey

@AutoService(Initializer::class)
class StoreInit : Initializer {
    override fun onInit(context: Context) {
        Store.log = true
        Store.getterHook = GetterHook { t, table, key ->
            null
        }
        Store.mmkvCryptKey {
            null
        }
    }
}