package io.github.xiaobaicz.store.mmkv

import io.github.xiaobaicz.store.Serialize
import io.github.xiaobaicz.store.serialize.GsonSerialize
import io.github.xiaobaicz.store.store

/**
 * 获取Store
 */
inline fun <reified T : Any> mmkvStore(
    serialize: Serialize = GsonSerialize
): Lazy<T> {
    return lazy { store(T::class, MMKVStore, serialize) }
}