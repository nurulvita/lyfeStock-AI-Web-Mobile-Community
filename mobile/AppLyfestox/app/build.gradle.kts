plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
//    id ("kotlin-kapt")
}

android {
    namespace = "com.example.applyfestox"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.applyfestox"
        minSdk = 25
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.datastore.preferences.core.jvm)
    implementation(libs.androidx.security.crypto.ktx)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.foundation.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation Compose

    implementation("androidx.navigation:navigation-compose:2.8.4")
    implementation("androidx.compose.ui:ui:1.7.5")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation ("androidx.activity:activity-ktx:1.9.3")
    implementation ("androidx.fragment:fragment-ktx:1.6.1")
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation ("com.google.accompanist:accompanist-coil:0.15.0")
    implementation ("androidx.security:security-crypto:1.0.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.core:core:1.15.0")
    implementation ("androidx.security:security-crypto-ktx:1.1.0-alpha06")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.core:core:1.15.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")


    // Jetpack ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp for logging HTTP requests (optional)
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

    // Core KTX for Kotlin extensions
    implementation("androidx.core:core-ktx:1.12.0")

    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.material:material:1.6.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.0")
    implementation("androidx.activity:activity-compose:1.8.0")

    implementation("mysql:mysql-connector-java:8.0.32")

//    implementation ("androidx.room:room-runtime:2.5.2")
//    kapt "androidx.room:room-compiler:2.5.2"
//    implementation ("androidx.room:room-ktx:2.5.2")



//    implementation ("androidx.camera:camera-core:1.5.0")
//    implementation ("androidx.camera:camera-lifecycle:1.5.0")
//    implementation ("androidx.camera:camera-view:1.5.0")
//    implementation ("androidx.compose.ui:ui:1.5.0")
//    implementation ("androidx.compose.material:material:1.5.0")
//    implementation ("io.coil-kt:coil-compose:2.4.0")




    // extended icons
    implementation(libs.androidx.material.icons.extended)

    // coil image
    implementation(libs.coil.compose)

    // accompanist
    implementation(libs.accompanist.systemuicontroller)

    // exoplayer
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)

    // Lottie
    implementation(libs.lottie.compose)

    //Data-Store
    implementation(libs.androidx.datastore.preferences)

    //Lifecycle
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // ACCOMPANIST
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.webview)

    //// CAMERA STUFF ////
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle.v140)
    implementation(libs.androidx.camera.view.v140)
    implementation(libs.androidx.camera.extensions)

}