buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(MyDependencies.kotlinGradlePlugin)
        classpath(MyDependencies.androidBuildTools)
        classpath(MyDependencies.sqlDelightGradlePlugin)
        classpath(MyDependencies.hiltGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
