package vip.oicp.xiaobaicz.lib.store.demo

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import vip.oicp.xiaobaicz.lib.store.demo.databinding.ActivityMainBinding
import vip.oicp.xiaobaicz.lib.store.demo.store.Local
import vip.oicp.xiaobaicz.lib.store.store

class MainActivity : FragmentActivity() {
    private val bind by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val local: Local = store()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        bind.lifecycleOwner = this
        bind.local = local
    }
}