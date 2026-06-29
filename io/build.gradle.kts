plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    `maven-publish`
}

group = "com.orange"
version = "1.0.0"

kotlin {
    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    listOf(iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Io"
            isStatic = true
        }
    }
    jvm()

    sourceSets {
        commonTest.dependencies { implementation(libs.kotlin.test) }
        val iosMain by creating { dependsOn(commonMain.get()) }
        iosArm64Main.get().dependsOn(iosMain)
        iosSimulatorArm64Main.get().dependsOn(iosMain)
    }
}

android {
    namespace = "com.orange.io"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

publishing {
    repositories {
        if (
            ((findProperty("gpr.user") as String?) ?: System.getenv("GITHUB_ACTOR")).isNullOrBlank().not() &&
            ((findProperty("gpr.key") as String?) ?: System.getenv("GITHUB_TOKEN")).isNullOrBlank().not()
        ) {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/addskya/orange-io")
                credentials {
                    username = (findProperty("gpr.user") as String?) ?: System.getenv("GITHUB_ACTOR")
                    password = (findProperty("gpr.key") as String?) ?: System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
    publications.withType<MavenPublication>().configureEach {
        pom {
            name.set("orange-io")
            description.set("Kotlin Multiplatform IO primitives for Orange modules")
        }
    }
}
