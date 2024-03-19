package io.github.xiaobaicz.store.mmkv

import android.content.Context
import com.google.auto.service.AutoService
import com.tencent.mmkv.MMKV
import io.github.xiaobaicz.initializer.Initializer

@AutoService(Initializer::class)
class MMKVInit : Initializer {
    override fun onInit(context: Context) {
        MMKV.initialize(context)
    }
}