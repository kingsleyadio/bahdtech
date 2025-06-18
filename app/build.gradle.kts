plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Add other plugins like kotlin-kapt, hilt, parcelize, etc.
    // id("kotlin-kapt") // Example if you were using kapt
    // id("dagger.hilt.android.plugin") // Example
    // id("kotlin-parcelize") // Example
}

android {
    namespace = "com.example.bahdtech" // Use assignment for namespace
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.bahdtech"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Direct string is fine
        // Or if you defined it in libs.versions.toml:
        // testInstrumentationRunner = libs.versions.androidxTestRunnerName.get()

        // vectorDrawables {
        //    useSupportLibrary = true
        // }
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Use isMinifyEnabled
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
        // Add other build types like debug if you have specific configurations
        // debug {
        //     applicationIdSuffix = ".debug"
        //     isMinifyEnabled = false
        // }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        // dataBinding = true // if you use dataBinding
        // compose = true // if you use Jetpack Compose
    }

    // If you have packagingOptions:
    // packagingOptions {
    //     resources {
    //         excludes += "/META-INF/{AL2.0,LGPL2.1}"
    //     }
    // }
}

dependencies {
    implementation(libs.kotlin.stdlib.jdk8) // Parentheses for configurations
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.swiperefreshlayout)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.common.java8)

    implementation(libs.appkommons.bootstrap)
    implementation(libs.appkommons.recyclerview)
    implementation(libs.appkommons.view)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.moshi)

    implementation(libs.moshi.core)
    implementation(libs.moshi.kotlin)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    // Add other Koin modules if needed
    // implementation(libs.koin.androidx.navigation)
    // implementation(libs.koin.androidx.compose) // if using Compose

    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    testImplementation(libs.koin.test) // Ensure koin-test is correctly defined in TOML

    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)

    // Examples for kapt or annotationProcessors if you use them:
    // kapt(libs.somelibrary.compiler)
    // annotationProcessor(libs.otherlibrary.processor)
}
