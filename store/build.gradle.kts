plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    `maven-publish`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "vip.oicp.xiaobaicz"
            artifactId = "store"
            version = "1.0.4"

            afterEvaluate {
                from(components["java"])
            }
        }
    }
}