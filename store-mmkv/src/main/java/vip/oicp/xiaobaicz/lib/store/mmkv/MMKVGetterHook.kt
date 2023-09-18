@file:Suppress("unused")

package vip.oicp.xiaobaicz.lib.store.mmkv

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import vip.oicp.xiaobaicz.lib.store.compat.GetterHook

@Suppress("UNCHECKED_CAST")
class MMKVGetterHook private constructor(private val kv: MMKV) : GetterHook {

    companion object {
        @JvmStatic
        fun default(): GetterHook = MMKVGetterHook(MMKV.defaultMMKV())
        @JvmStatic
        fun create(kv: MMKV): GetterHook = MMKVGetterHook(kv)
    }

    override fun find(returnType: Class<*>, table: String, key: String): Any? {
        return when {
            isInt(returnType) -> kv.decodeInt(key, 0)
            isLong(returnType) -> kv.decodeLong(key, 0L)
            isFloat(returnType) -> kv.decodeFloat(key, 0f)
            isDouble(returnType) -> kv.decodeDouble(key, 0.0)
            isBool(returnType) -> kv.decodeBool(key, false)
            isString(returnType) -> kv.decodeString(key)
            isBytes(returnType) -> kv.decodeBytes(key)
            isStringSet(returnType) -> tryFunc { kv.decodeStringSet(key) }
            isParcelable(returnType) -> tryFunc { kv.decodeParcelable(key, returnType as Class<Parcelable>) }
            else -> throw IllegalArgumentException("Return type is not supported")
        }
    }

    private fun isInt(type: Class<*>): Boolean {
        return type == Int::class.java || type == java.lang.Integer::class.java
    }

    private fun isLong(type: Class<*>): Boolean {
        return type == Long::class.java || type == java.lang.Long::class.java
    }

    private fun isFloat(type: Class<*>): Boolean {
        return type == Float::class.java || type == java.lang.Float::class.java
    }

    private fun isDouble(type: Class<*>): Boolean {
        return type == Double::class.java || type == java.lang.Double::class.java
    }

    private fun isBool(type: Class<*>): Boolean {
        return type == Boolean::class.java || type == java.lang.Boolean::class.java
    }

    private fun isString(type: Class<*>): Boolean {
        return type == String::class.java || type == java.lang.String::class.java
    }

    private fun isBytes(type: Class<*>): Boolean {
        return type == ByteArray::class.java
    }

    private fun isStringSet(type: Class<*>): Boolean {
        return Set::class.java.isAssignableFrom(type)
    }

    private fun isParcelable(type: Class<*>): Boolean {
        return Parcelable::class.java.isAssignableFrom(type)
    }

    private fun tryFunc(func: () -> Any?): Any? = try {
        func()
    } catch (t: Throwable) {
        null
    }

}
