package vip.oicp.xiaobaicz.lib.store.demo.entity

data class Account(
    val name: String,
    val password: String,
) {
    companion object {
        @JvmStatic
        fun create(name: String, password: String): Account {
            return Account(name, password)
        }
    }
}