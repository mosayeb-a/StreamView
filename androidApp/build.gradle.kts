plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") version MyDependencies.kotlinVersion
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.ma.streamview.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.ma.streamview.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.ma.streamview.TestHiltRunner"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = MyDependencies.composeVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":shared"))

    // compose
    implementation(MyDependencies.composeUi)
    implementation(MyDependencies.composeUiTooling)
    implementation(MyDependencies.composeUiToolingPreview)
    implementation(MyDependencies.composeFoundation)
    implementation(MyDependencies.composeMaterial)
    implementation(MyDependencies.activityCompose)
    implementation(MyDependencies.composeNavigation)
    implementation(MyDependencies.coilCompose)
    implementation(MyDependencies.composeAnimation)
    implementation(MyDependencies.composeUtil)


    // hilt
    implementation(MyDependencies.hiltAndroid)
//    implementation(MyDependencies.constraintLayout)
    kapt(MyDependencies.hiltAndroidCompiler)
    kapt(MyDependencies.hiltCompiler)
    implementation(MyDependencies.hiltNavigationCompose)

    // ktor
    implementation(MyDependencies.ktorAndroid)

    // exoPlayer
    implementation(MyDependencies.exoPlayer)
    implementation(MyDependencies.exoPlayerUi)
    implementation(MyDependencies.exoPlayerHls)
    implementation(MyDependencies.mediaSession)

    // test
    androidTestImplementation(MyDependencies.testRunner)
    androidTestImplementation(MyDependencies.jUnit)
    androidTestImplementation(MyDependencies.composeTesting)
    androidTestImplementation(MyDependencies.rules)
    debugImplementation(MyDependencies.composeTestManifest)

    kaptAndroidTest(MyDependencies.hiltAndroidCompiler)
    androidTestImplementation(MyDependencies.hiltTesting)
}