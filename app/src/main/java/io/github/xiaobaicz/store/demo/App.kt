package io.github.xiaobaicz.store.demo

import android.app.Application
import com.tencent.mmkv.MMKV
import io.github.xiaobaicz.store.debug.storeLog

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        storeLog = true
    }
}