plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
//    id("dagger.hilt.android.plugin")
//    id("kotlin-kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.harmonyhub"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.harmonyhub"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
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
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation("androidx.compose.runtime:runtime-livedata:1.5.1")

    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("com.squareup.okhttp3:okhttp:4.9.3") // OkHttp - thư viện HTTP Client
    implementation ("org.json:json:20210307") // org.json để xử lý JSON
    implementation ("androidx.media3:media3-exoplayer:1.1.0")
    implementation ("androidx.media3:media3-ui:1.1.0")

    implementation("io.coil-kt:coil-compose:2.2.2") // Phiên bản mới nhất
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-alpha03")
    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
}