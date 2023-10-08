# SharedPreferences Storage Module
Specific implementation of SharedPreferences storage

## Example
~~~ Kotlin
class App : Application() {
    override fun onCreate() {
        // ...
        ContextProvider.provider = ContextProvider { this }
    }
}
~~~
~~~ Kotlin
@SPStore
interface Local {
    // ...
}
~~~

### SharedPreferences Data Migration
- Migration
~~~ Kotlin
@SPStore
interface Local {
    // Mapping old SharedPreferences data with Migrate annotation and migration
    @get:Migrate("<Original Key>")
    var version: String?
}
~~~

- Old SharedPreferences data Hook
~~~ Kotlin
class App : Application() {
    override fun onCreate() {
        // ...
        storeGetterHook = SPGetterHook.create(SharedPreferences)
    }
}
~~~
