package vip.oicp.xiaobaicz.lib.store.demo

import android.app.Application
import com.tencent.mmkv.MMKV
import vip.oicp.xiaobaicz.lib.store.storeDebug

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        storeDebug = true
    }
}