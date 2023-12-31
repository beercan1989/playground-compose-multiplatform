import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times in each subproject's classloader
    kotlin("jvm") apply false
    kotlin("android") apply false
    kotlin("multiplatform") apply false

    id("com.android.library") apply false
    id("com.android.application") apply false

    id("org.jetbrains.compose") apply false
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev/")
    }

    configurations.all {
        val conf = this
        // Currently it's necessary to make the android build work properly
        conf.resolutionStrategy.eachDependency {
            val isWasm = conf.name.contains("wasm", true)
            val isJs = conf.name.contains("js", true)
            val isComposeGroup = requested.module.group.startsWith("org.jetbrains.compose")
            val isComposeCompiler = requested.module.group.startsWith("org.jetbrains.compose.compiler")
            if (isComposeGroup && !isComposeCompiler && !isWasm && !isJs) {
                val composeVersion: String by project
                useVersion(composeVersion)
            }
            if (requested.module.name.startsWith("kotlin-stdlib")) {
                val kotlinVersion: String by project
                useVersion(kotlinVersion)
            }
        }
    }
}

tasks.withType<Wrapper>().configureEach {
    distributionType = Wrapper.DistributionType.ALL
}
