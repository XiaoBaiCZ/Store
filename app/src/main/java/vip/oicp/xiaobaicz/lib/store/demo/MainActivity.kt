package vip.oicp.xiaobaicz.lib.store.demo

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import vip.oicp.xiaobaicz.lib.store.demo.databinding.ActivityMainBinding
import vip.oicp.xiaobaicz.lib.store.demo.entity.Account
import vip.oicp.xiaobaicz.lib.store.demo.store.Local
import vip.oicp.xiaobaicz.lib.store.store

class MainActivity : FragmentActivity() {
    private val bind by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val local: Local = store()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        bind.save.setOnClickListener {
            val account = Account(
                bind.name.text.toString(),
                bind.password.text.toString(),
            )
            onSaveAccount(account)
        }
        onChangeUI(local.account)
    }
    private fun onSaveAccount(account: Account) {
        local.account = account
    }
    private fun onChangeUI(account: Account?) {
        bind.name.setText(account?.name)
        bind.password.setText(account?.password)
    }
}