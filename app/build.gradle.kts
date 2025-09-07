plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    //kotlin("jvm") version "2.2.10"
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.example.genaitracking"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.genaitracking"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packagingOptions { // <-- This block will be added
        resources.excludes.add("META-INF/INDEX.LIST")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    val ktorVersion = "2.3.12" // Or the latest stable version recommended for Kotlin 2.0.x
    implementation(platform("io.ktor:ktor-bom:$ktorVersion")) // Use the defined version
    implementation("io.ktor:ktor-client-core") // Version managed by BOM
    implementation("io.ktor:ktor-client-cio")  // Version managed by BOM
    // Ktor includes SLF4J, Logback is a common implementation
    //implementation("ch.qos.logback:logback-classic:1.4.14")
    //implementation("ch.qos.logback:logback-classic:+")// Or a newer compatible version like 1.5.x
    implementation("com.github.tony19:logback-android:3.0.0")
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("io.ktor:ktor-client-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-client-logging")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")

    implementation("com.patrykandpatrick.vico:core:1.14.0")
    implementation(libs.vico.compose)
    //implementation(libs.vico.compose.m2)
    implementation(libs.vico.compose.m3)
}