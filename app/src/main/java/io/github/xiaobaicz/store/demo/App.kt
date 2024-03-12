package io.github.xiaobaicz.store.demo

import android.app.Application
import io.github.xiaobaicz.store.Store
import io.github.xiaobaicz.store.debug.storeLog

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        storeLog = true
        Store.init(this)
    }
}