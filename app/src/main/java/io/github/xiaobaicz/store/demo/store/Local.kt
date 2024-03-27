package io.github.xiaobaicz.store.demo.store

import io.github.xiaobaicz.store.Clear
import io.github.xiaobaicz.store.annotation.Serialize
import io.github.xiaobaicz.store.annotation.Store
import io.github.xiaobaicz.store.demo.entity.Account
import io.github.xiaobaicz.store.mmkv.MMKVStore
import io.github.xiaobaicz.store.serialize.gson.GsonSerialize

@Store(MMKVStore::class)
@Serialize(GsonSerialize::class)
interface Local : Clear {
    var account: Account?
    var version: String?
}