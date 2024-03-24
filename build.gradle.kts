// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlinVersion by extra("1.9.20")

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath ("com.google.gms:google-services:4.3.15")
        classpath("com.android.tools.build:gradle:8.2.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")
    }
}


tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}