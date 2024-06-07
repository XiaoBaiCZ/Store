package io.github.xiaobaicz.store.demo.store

import io.github.xiaobaicz.store.Clear
import io.github.xiaobaicz.store.demo.entity.Account

interface Local : Clear {
    var account: Account?
    var version: String?
}