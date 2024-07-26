import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.5.10"
}

group = "com.ma.streamview"
version = "1.0.0"


repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

kotlin {
    jvm {
        withJava()
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
    }
    sourceSets {
        val jvmMain by getting {
            kotlin.srcDirs("src/jvmMain/kotlin")
            dependencies {
                implementation(compose.desktop.currentOs)
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                api(compose.ui)
                implementation("org.jetbrains.skiko:skiko-awt-runtime-windows-x64:0.7.85") // Add the Skiko dependency


                implementation(project(":shared"))
            }
        }

    }

}

compose.desktop {
    application {
        mainClass = "com.ma.streamview.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MyProject"
            packageVersion = "1.0.0"
//            macOS {
//                bundleID = "com.domain.project"
//            }
        }

    }

}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}