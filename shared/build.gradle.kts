import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")

    id("com.android.library")

    id("org.jetbrains.compose")
}

version = "1.0.0-SNAPSHOT"

val ktorVersion: String by project
val kotlinVersion: String by project
val composeVersion: String by project
val atomicfuVersion: String by project
val composeWasmVersion: String by project
val kotlinxCoroutinesVersion: String by project
val kotlinxSerializationVersion: String by project

kotlin {

    jvmToolchain(11)

    androidTarget()

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasm {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.ui)
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.material3)
                @OptIn(ExperimentalComposeLibrary::class)
                api(compose.components.resources)

                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("org.jetbrains.kotlinx:atomicfu:$atomicfuVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinxSerializationVersion")
            }
        }

        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.6.1")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.9.0")

                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)

                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:$kotlinxCoroutinesVersion")
            }
        }

        val wasmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:$ktorVersion")
            }
        }
    }
}

android {
    compileSdk = 33
    namespace = "uk.co.baconi"
    defaultConfig {
        minSdk = 26
        targetSdk = 33
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

compose {
    kotlinCompilerPlugin.set(composeWasmVersion)

    // Fixes lintVitalAnalyzeRelease and lintAnalyzeDebug
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=$kotlinVersion")
}
