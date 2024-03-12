plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    `maven-publish`
    signing
}

android {
    namespace = "io.github.xiaobaicz.store.mmkv"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(path = ":store"))
    implementation(libs.mmkv)
    implementation(libs.auto.service.annotations)
    kapt(libs.auto.service)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "io.github.xiaobaicz"
            artifactId = "store-mmkv"
            version = "1.0.7"

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name = "store-mmkv"
                description = "java abstract store"
                url = "https://github.com/xiaobaicz/store"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        name = "bocheng.lao"
                        email = "xiaojinjincz@outlook.com"
                        organization = "bocheng.lao"
                        organizationUrl = "https://xiaobaicz.github.io"
                    }
                }
                scm {
                    connection = "scm:git:https://github.com/xiaobaicz/store.git"
                    developerConnection = "scm:git:https://github.com/xiaobaicz/store.git"
                    url = "https://github.com/xiaobaicz/store/tree/main"
                }
            }
        }
    }
    repositories {
        maven {
            url = uri("../build/maven")
        }
    }
}

signing {
    sign(publishing.publications["release"])
}