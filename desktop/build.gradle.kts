import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")

    id("org.jetbrains.compose")
}

kotlin {
    jvmToolchain(11)
    jvm("desktop")
    sourceSets {
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":shared"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "uk.co.baconi.MainKt"
        buildTypes.release.proguard {
            configurationFiles.from(project.file("src/desktopMain/resources/compose-desktop.pro"))
        }
        nativeDistributions {
            targetFormats(TargetFormat.Exe, TargetFormat.Msi, TargetFormat.Dmg, TargetFormat.Deb)
            packageName = "Compose"
            packageVersion = "1.0.0"
            windows {
                upgradeUuid = "27CAC61C-A64B-4AB4-899C-5A33C0D95353"
            }
        }
    }
}

compose {
    val composeWasmVersion: String by project
    kotlinCompilerPlugin.set(composeWasmVersion)
}
