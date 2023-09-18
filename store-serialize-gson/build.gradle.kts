plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-kapt")
    `maven-publish`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    implementation(project(path = ":store"))
    implementation(libs.gson)
    implementation(libs.auto.service.annotations)
    kapt(libs.auto.service)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "vip.oicp.xiaobaicz"
            artifactId = "store-serialize-gson"
            version = "1.0.0"

            afterEvaluate {
                from(components["java"])
            }
        }
    }
}