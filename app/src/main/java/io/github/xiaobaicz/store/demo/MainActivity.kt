package io.github.xiaobaicz.store.demo

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import io.github.xiaobaicz.store.demo.databinding.ActivityMainBinding
import io.github.xiaobaicz.store.demo.store.Local
import io.github.xiaobaicz.store.mmkv.mmkvStore

class MainActivity : FragmentActivity() {
    private val bind by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val local by mmkvStore<Local>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        bind.lifecycleOwner = this
        bind.local = local
    }
}