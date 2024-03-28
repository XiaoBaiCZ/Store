package io.github.xiaobaicz.store.serialize.gson

import com.google.gson.Gson
import io.github.xiaobaicz.store.Serialize

class GsonSerialize : Serialize {
    private val gson = Gson()
    override fun serialize(target: Any?): String? {
        target ?: return null
        return gson.toJson(target)
    }

    override fun deserialize(type: Class<*>, serialize: String?): Any? {
        serialize ?: return null
        return gson.fromJson(serialize, type)
    }
}