# Store
Abstract storage

## Tutorials
1. Declare an interface
2. Declaring interface properties (Support for the use of customized structures)
3. Getting an interface instance using the store() method
4. Read and write using Getter/Setter methods of interface properties

### Example
~~~ Kotlin
// Declare an interface
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

## Dependencie
~~~ kotlin
dependencies {
  // ...
  implementation("io.github.xiaobaicz:store:3.0.0")         // empty store
  // Or
  implementation("io.github.xiaobaicz:store-mmkv:3.0.0")    // mmkv store
}
~~~

