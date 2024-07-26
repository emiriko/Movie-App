plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.hiltPlugin)
}

android {
    namespace = "com.alvaro.movieapp.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "API_BASE_URL", "\"https://api.themoviedb.org/3/\"")
        buildConfigField(
            "String",
            "API_KEY",
            "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NjVkZDA2NDE0MGNmNDgxYTcyZjJkZjUyMTA0N2I5OCIsIm5iZiI6MTcyMTQ2MDU3Ni4yNDY2MDYsInN1YiI6IjY2OWI2NmI1MmRiNDMwMzczMzA5OWEwMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Xzp30nrdFUkw10dBuvFJI3aQOxRouSoZ6FQJpiMxM9U\""
        )
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
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
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
}