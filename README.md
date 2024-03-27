# Store
Abstract storage

## Tutorials
1. Declare an interface
2. Use annotations to indicate the specific storage/serialization method used
3. Declaring interface properties (Support for the use of customized structures)
4. Getting an interface instance using the store() method
5. Read and write using Getter/Setter methods of interface properties

### Example
~~~ Kotlin
// Declare an interface
@MMKVStore      // Storage with MMVK
@GsonSerialize  // Serialization with Gson
interface Local : Clear {
    var account: Account?  // Declaring interface properties (customized structures)
    var version: String?   // Declaring interface properties
}

// Customized structures
class Account(
    val name: String,
    val password: String,
)

// Using the store() method
val local = store<Local>()

// Read and Write
local.account = Account("xiaobai", "111111")
println(local.account)

// clear
local.clear()
~~~

- Debug
~~~ Kotlin
class App : Application() {
    override fun onCreate() {
        // ...
        storeLog = true // Print log
    }
}
~~~

- Customizable (Service registration by SPI)
~~~ Kotlin
// Using Google Auto Service to automatically configure services files

// Store
@AutoService(Store::class)
class MMKVStoreImpl : Store {
    // ...
    override fun filter(clazz: Class<*>): Boolean {
        // Self-defining annotations for filtering
        return clazz.getAnnotation(MMKVStore::class.java) != null
    }
}

// Serialize
@AutoService(Serialize::class)
class GsonSerializeImpl : Serialize {
    // ...
    override fun filter(clazz: Class<*>): Boolean {
        // Self-defining annotations for filtering
        return clazz.getAnnotation(GsonSerialize::class.java) != null
    }
}
~~~

## Dependencie
~~~ kotlin
dependencies {
  // ...
  implementation("io.github.xiaobaicz:store:1.1.0")                 // require
  // Store Impl Choose at least one
  implementation("io.github.xiaobaicz:store-mem:1.1.0")             // optional
  implementation("io.github.xiaobaicz:store-mmkv:1.1.0")            // optional
  // Serialize Impl Choose at least one
  implementation("io.github.xiaobaicz:store-serialize-gson:1.1.0")  // optional
}
~~~

