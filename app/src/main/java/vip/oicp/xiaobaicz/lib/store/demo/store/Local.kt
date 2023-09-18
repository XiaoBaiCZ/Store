package vip.oicp.xiaobaicz.lib.store.demo.store

import vip.oicp.xiaobaicz.lib.store.demo.entity.Account
import vip.oicp.xiaobaicz.lib.store.mmkv.annotation.MMKVStore
import vip.oicp.xiaobaicz.lib.store.serialize.gson.annotation.GsonSerialize

@MMKVStore
@GsonSerialize
interface Local {
    var account: Account?
    var version: String?
}