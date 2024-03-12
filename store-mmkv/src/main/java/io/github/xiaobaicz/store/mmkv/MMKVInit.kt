package io.github.xiaobaicz.store.mmkv

import android.app.Application
import com.google.auto.service.AutoService
import com.tencent.mmkv.MMKV
import io.github.xiaobaicz.store.spi.StoreInit

@AutoService(StoreInit::class)
class MMKVInit : StoreInit {
    override fun init(any: Any?) {
        if (any !is Application) return
        MMKV.initialize(any)
    }
}