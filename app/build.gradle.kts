

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("kotlin-android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")

}

android {
    namespace = "com.example.newswave"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.newswave"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // Add them as build config fields /

        buildConfigField("String", "API_KEY", project.findProperty("API_KEY") as String)
        buildConfigField("String", "BASE_URL", project.findProperty("BASE_URL") as String)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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

    buildFeatures{
        viewBinding = true
        buildConfig = true
    }

}

dependencies {

    val lifecycleVersion = "2.7.0"
    val archVersion = "2.2.0"
    val roomVersion = "2.6.1"
    val coroutinesVersion = "1.8.1"
    val retrofitVersion = "2.11.0"
    val interceptorVersion = "4.12.0"
    val glideVersion = "4.16.0"
    val kotlin_version = "1.7.0"
    val nav_version = "2.7.7"
    val hilt_version = "2.46"

    // ViewModel, livedata,
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")

    //Annotation processor
    kapt("androidx.lifecycle:lifecycle-compiler:$lifecycleVersion")

    //Annotations
    implementation("androidx.core:core-ktx:1.12.0")


    //For room
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
    testImplementation("androidx.room:room-testing:$roomVersion")


    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")


    //Retrofit, gson converter
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    //Okhttp Logging Interceptor to get network communication details as log results, having
    //logging interceptor is very useful for bug fixing.

    implementation("com.squareup.okhttp3:logging-interceptor:$interceptorVersion")

    //Glide is a media management framework for Android, we will use it in our app for image loading.

    //implementation ("com.github.bumptech.glide:glide:$glideVersion")

    //MockWebServer
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")

    //truth
    testImplementation("com.google.truth:truth:1.4.2")

    //okhttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    //Hilt
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-compiler:$hilt_version")

    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}


//Hilt
kapt {
    correctErrorTypes = true
}

