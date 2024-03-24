
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")

}

hilt {
    enableAggregatingTask = true
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
}

android {
    namespace = "com.example.forecast"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.forecast"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
}
extra["retrofit_version"] = "2.9.0"
extra["okhttp_version"] = "4.12.0"
extra["richtext_version"] = "0.16.0"

val retrofit_version: String by extra
val okhttp_version: String by extra
val richtext_version: String by extra

apply(plugin = "com.google.gms.google-services")

dependencies {
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.4")
    // Navigation Fragment & UI KTX already covered by navigation-compose
    implementation("androidx.compose.ui:ui:1.6.4")
    implementation("androidx.compose.material:material:1.6.4")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.4")
    implementation("androidx.compose.material:material-icons-core:1.6.4")
    implementation("androidx.compose.material:material-icons-extended:1.6.4")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("com.google.accompanist:accompanist-pager:0.33.2-alpha")
    implementation("androidx.compose.animation:animation:1.6.4")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.firebase:firebase-firestore-ktx:24.11.0")
    implementation("io.coil-kt:coil:2.3.0")
    implementation ("io.coil-kt:coil-compose:2.3.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")
    kapt ("androidx.hilt:hilt-compiler:1.2.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // System bars customization
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.28.0")
    implementation ("androidx.compose.ui:ui-viewbinding:1.6.4")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // Import the BoM for the Firebase platform
//    implementation platform("com.google.firebase:firebase-bom:31.3.0")
//    implementation ("com.google.firebase:firebase-analytics-ktx")
//
//    // Declare the dependency for the Firestore library
//    implementation ("com.google.firebase:firebase-firestore-ktx")
//
//    // Dependency injection
//    implementation ("com.google.dagger:hilt-android:2.44")
//    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
//    kapt ("com.google.dagger:hilt-compiler:2.44")
//    implementation ("com.google.code.gson:gson:2.9.0")
//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
//
    // Networking
    implementation ("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation ("com.squareup.okhttp3:okhttp:$okhttp_version")
    implementation ("com.squareup.okhttp3:logging-interceptor:$okhttp_version")

    // UI/UX Utils
    implementation ("com.halilibo.compose-richtext:richtext-commonmark:${richtext_version}")
    implementation ("com.halilibo.compose-richtext:richtext-ui-material:${richtext_version}")
    implementation ("com.halilibo.compose-richtext:richtext-ui-material3:${richtext_version}")
}

