# Store
抽象化存储

~~~ kotlin
// kotlin - build.gradle.kts
allprojects {
  repositories {
    // ...
    maven { setUrl("https://jitpack.io") }
  }
}

// kotlin - build.gradle.kts
dependencies {
  // ...
  implementation("com.github.xiaobai-cz.store:store:1.0.1")                 // require
  // Store Impl Choose at least one
  implementation("com.github.xiaobai-cz.store:store-mem:1.0.1")             // optional
  implementation("com.github.xiaobai-cz.store:store-mmkv:1.0.1")            // optional
  // Serialize Impl Choose at least one
  implementation("com.github.xiaobai-cz.store.Store:store-serialize-gson:1.0.1")  // optional
}
~~~

~~~ gradle
// gradle - root build.gradle
allprojects {
  repositories {
    // ...
    maven { url 'https://jitpack.io' }
  }
}

// gradle - build.gradle
dependencies {
  // ...
  implementation 'com.github.xiaobai-cz.store:store:1.0.1'                  // require
  // Store Impl Choose at least one
  implementation 'com.github.xiaobai-cz.store:store-mem:1.0.1'              // optional
  implementation 'com.github.xiaobai-cz.store:store-mmkv:1.0.1'             // optional
  // Serialize Impl Choose at least one
  implementation 'com.github.xiaobai-cz.store:store-serialize-gson:1.0.1'   // optional
}
~~~
