plugins {
    alias(libs.plugins.androidDynamicFeature)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.hiltPlugin)
}
android {
    namespace = "com.alvaro.movieapp.favorite"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":app"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.room)
    implementation(libs.androidx.roomKtx) // For suspend
    implementation(libs.lifecycleViewModelKtx)
    implementation(libs.hilt)
    implementation(libs.hiltNavigationCompose)

    implementation(libs.retrofit)
    implementation(libs.retrofitGson)
    implementation(libs.loggingInterceptor)
    implementation(libs.coilCompose)
    implementation(libs.accompanistSystemuicontroller)
    implementation(libs.jetpackLoading)
    implementation(libs.splashScreen)
    implementation(libs.iconsax)

    ksp(libs.hiltCompiler)
    ksp(libs.androidx.roomCompiler)
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.annotation)
}