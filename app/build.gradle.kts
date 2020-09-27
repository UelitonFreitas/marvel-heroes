plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(Versions.Android.SDKCompile)
    buildToolsVersion(Versions.buildToolsVersion)

    defaultConfig {
        applicationId = "com.hero.marvelheroes"
        minSdkVersion(Versions.Android.minSdkVersion)
        targetSdkVersion(Versions.Android.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        all {
            buildConfigField("String", "MARVEL_API_KEY", EnvironmentVariables.MARVEL_API_KEY?.betweenQuotes())
            buildConfigField("String", "MARVEL_PRIVATE_API_KEY", EnvironmentVariables.MARVEL_PRIVATE_API_KEY?.betweenQuotes())
        }

        create("developer") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(Dependencies.Kotlin.kotlinStdLib)
    implementation(Dependencies.Android.androidXCoreLibrary)
    implementation(Dependencies.Android.androidXAppCompat)
    implementation(Dependencies.Android.androidXConstraintLayout)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.gson)
    implementation(Dependencies.gsonConverter)
    implementation(Dependencies.loggingInterceptor)

    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.mockK)

    androidTestImplementation(Dependencies.Test.androidXjUnit)
    androidTestImplementation(Dependencies.Test.androidXEspresso)
    androidTestImplementation(Dependencies.Test.androidMockK)
}