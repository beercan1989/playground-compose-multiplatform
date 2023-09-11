rootProject.name = "compose"

include(":shared")
include(":android")
include(":desktop")
include(":website")

pluginManagement {

    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
    }

    plugins {
        val agpVersion: String by settings
        val kotlinVersion: String by settings
        val composeWasmVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("android") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion

        id("com.android.base") version agpVersion
        id("com.android.library") version agpVersion
        id("com.android.application") version agpVersion

        id("org.jetbrains.compose") version composeWasmVersion
    }
}
