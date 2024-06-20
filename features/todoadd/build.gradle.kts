plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.dagger.hilt)
}

android {
    namespace = "com.app.todo.features.todoadd"
    compileSdk = 34

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
        kotlinCompilerExtensionVersion = "1.5.10"
    }
}

dependencies {

    implementation(project(":business:di"))
    implementation(project(":business:domain:main"))
    implementation(project(":business:domain:model"))

    implementation(project(":common:domain:api"))
    implementation(project(":common:general:extensions"))
    implementation(project(":common:general:models"))
    implementation(project(":common:navigation"))
    implementation(project(":common:presentation:widgets"))
    implementation(project(":common:ui:components"))
    implementation(project(":common:ui:resources:drawables"))
    implementation(project(":common:ui:resources:strings"))
    implementation(project(":common:ui:theme"))

    implementation(libs.androidx.activity.compose)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(libs.androidx.material3)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.animation)

    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
