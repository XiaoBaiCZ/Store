@file:Suppress("unused")
package io.github.xiaobaicz.store.serialize.gson

import com.google.auto.service.AutoService
import com.google.gson.Gson
import io.github.xiaobaicz.store.Serialize
import io.github.xiaobaicz.store.serialize.gson.annotation.GsonSerialize

@AutoService(Serialize::class)
class GsonSerializeImpl : Serialize {
    private val gson = Gson()
    override fun serialize(target: Any?): String? {
        target ?: return null
        return gson.toJson(target)
    }

    override fun deserialize(type: Class<*>, serialize: String?): Any? {
        serialize ?: return null
        return gson.fromJson(serialize, type)
    }

    override fun filter(clazz: Class<*>): Boolean {
        return clazz.getAnnotation(GsonSerialize::class.java) != null
    }
}