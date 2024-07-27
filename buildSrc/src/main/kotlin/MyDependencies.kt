object MyDependencies {
    // Compose
    private const val activityComposeVersion = "1.8.0"
    const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"
    const val composeVersion = "1.5.4"
    const val composeUi = "androidx.compose.ui:ui:$composeVersion"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
    const val composeFoundation = "androidx.compose.foundation:foundation:$composeVersion"
    const val composeAnimation = "androidx.compose.animation:animation-graphics-android:$composeVersion"
    const val constraintLayout =  "androidx.constraintlayout:constraintlayout-compose:1.0.1"
    const val composeMaterial = "androidx.compose.material3:material3:1.1.2"
    const val composeUtil ="androidx.compose.ui:ui-util:$composeVersion"


    private const val composeNavigationVersion = "2.7.4"
    const val composeNavigation = "androidx.navigation:navigation-compose:$composeNavigationVersion"


    // Coil
    private const val coilComposeVersion = "2.1.0"
    const val coilCompose = "io.coil-kt:coil-compose:$coilComposeVersion"

    // Kotlin Data Time
    private const val dateTimeVersion = "0.4.0"
    const val kotlinDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:$dateTimeVersion"

    // Hilt
    private const val hiltVersion = "2.49"
    private const val hiltCompilerVersion = "1.0.0"
    const val hiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:$hiltCompilerVersion"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:$hiltCompilerVersion"
    const val annotation = "androidx.annotation:annotation-experimental:1.3.1"

    // Ktor
    private const val ktorVersion = "2.3.7"
    const val ktorCore = "io.ktor:ktor-client-core:$ktorVersion"
    const val ktorSerialization = "io.ktor:ktor-client-content-negotiation:$ktorVersion"
    const val ktorSerializationJson = "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion"
    const val ktorCIO = "io.ktor:ktor-client-cio:$ktorVersion"
    const val ktorAndroid = "io.ktor:ktor-client-android:$ktorVersion"
    const val ktorIOS = "io.ktor:ktor-client-ios:$ktorVersion"
    const val ktorLogging = "io.ktor:ktor-client-logging:$ktorVersion"

    // ExoPlayer
    private const val exoPlayerVersion = "1.2.0"
    const val exoPlayer = "androidx.media3:media3-exoplayer:$exoPlayerVersion"
    const val exoPlayerUi = "androidx.media3:media3-ui:$exoPlayerVersion"
    const val exoPlayerHls = "androidx.media3:media3-exoplayer-hls:$exoPlayerVersion"
    const val mediaSession = "androidx.media3:media3-session:$exoPlayerVersion"

    // Gradle Plugin
    const val kotlinVersion = "1.9.20"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

    private const val gradleVersion = "8.2.0"
    const val androidBuildTools = "com.android.tools.build:gradle:$gradleVersion"

    private const val sqlDelightGradleVersion = "1.5.3"
    const val sqlDelightGradlePlugin =
        "com.squareup.sqldelight:gradle-plugin:$sqlDelightGradleVersion"

    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"

    // SqlDelight
    private const val sqlDelightVersion = "1.5.5"
    const val sqlDelightRuntime = "com.squareup.sqldelight:runtime:$sqlDelightVersion"
    const val sqlDelightAndroidDriver = "com.squareup.sqldelight:android-driver:$sqlDelightVersion"
    const val sqlDelightNativeDriver = "com.squareup.sqldelight:native-driver:$sqlDelightVersion"
    const val sqlDelightCoroutinesExtensions =
        "com.squareup.sqldelight:coroutines-extensions:$sqlDelightVersion"

    // Lint
    const val lint = "com.android.tools.lint:lint-gradle:31.2.0"

    // Test
    private const val assertKVersion = "0.25"
    const val assertK = "com.willowtreeapps.assertk:assertk:$assertKVersion"

    private const val turbineVersion = "0.7.0"
    const val turbine = "app.cash.turbine:turbine:$turbineVersion"

    private const val jUnitVersion = "4.13.2"
    const val jUnit = "junit:junit:$jUnitVersion"

    private const val testRunnerVersion = "1.5.1"
    const val testRunner = "androidx.test:runner:$testRunnerVersion"

    const val composeTesting = "androidx.compose.ui:ui-test-junit4:$composeVersion"
    const val composeTestManifest = "androidx.compose.ui:ui-test-manifest:$composeVersion"

    const val hiltTesting = "com.google.dagger:hilt-android-testing:$hiltVersion"

    private const val rulesVersion = "1.5.0"
    const val rules = "androidx.test:rules:$rulesVersion"


}