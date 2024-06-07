package io.github.xiaobaicz.store.mmkv

import android.content.Context
import com.google.auto.service.AutoService
import com.tencent.mmkv.MMKV
import io.github.xiaobaicz.store.StoreInit

@AutoService(StoreInit::class)
class MMKVStoreInit : StoreInit {
    override fun onInit(context: Context) {
        MMKV.initialize(context)
    }
}