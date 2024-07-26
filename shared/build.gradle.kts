plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version MyDependencies.kotlinVersion
    id("com.squareup.sqldelight")
}

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

//    cocoapods {
//        summary = "Some description for the Shared Module"
//        homepage = "Link to the Shared Module homepage"
//        version = "1.0"
//        ios.deploymentTarget = "14.1"
//        podfile = project.file("../iosApp/Podfile")
//        framework {
//            isStatic = false
//            baseName = "shared"
//        }
//    }
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
    }

    sourceSets {
        val desktopMain by getting {
            dependencies {
                implementation(MyDependencies.ktorCIO)
                implementation(MyDependencies.ktorLogging)

            }
        }

        val commonMain by getting {
            dependencies {
                implementation(MyDependencies.ktorCore)
                implementation(MyDependencies.ktorSerialization)
                implementation(MyDependencies.ktorSerializationJson)
                implementation(MyDependencies.sqlDelightRuntime)
                implementation(MyDependencies.sqlDelightCoroutinesExtensions)
                implementation(MyDependencies.kotlinDateTime)
                implementation(MyDependencies.ktorLogging)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(MyDependencies.assertK)
                implementation(MyDependencies.turbine)
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("org.jetbrains.kotlin:kotlin-test:1.8.10")
                implementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.10")
                implementation("io.mockk:mockk-common:1.12.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(MyDependencies.ktorAndroid)
                implementation(MyDependencies.sqlDelightAndroidDriver)
                implementation(MyDependencies.ktorLogging)

            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation(MyDependencies.ktorIOS)
                implementation(MyDependencies.sqlDelightNativeDriver)
                implementation(MyDependencies.ktorLogging)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
    task("testClasses")
}

android {
    namespace = "com.ma.streamview"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
        targetSdk = 34
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    database("StreamDatabase") {
        packageName = "com.ma.streamview.database"
        sourceFolders = listOf("sqldelight")
    }
}