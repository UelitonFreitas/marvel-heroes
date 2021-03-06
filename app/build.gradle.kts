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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Dependencies.Kotlin.kotlinStdLib)
    implementation(Dependencies.Android.androidXCoreLibrary)
    implementation(Dependencies.Android.androidXAppCompat)
    implementation(Dependencies.Android.androidXConstraintLayout)
    implementation(Dependencies.Android.swipeToRefresh)
    implementation(Dependencies.Android.recyclerView)
    implementation(Dependencies.Android.androidXNavigationFragment)
    implementation(Dependencies.Android.androidXNavigationUi)

    implementation(Dependencies.retrofit)
    implementation(Dependencies.gson)
    implementation(Dependencies.gsonConverter)
    implementation(Dependencies.loggingInterceptor)
    implementation(Dependencies.picasso)

    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.mockK)

    androidTestImplementation(Dependencies.Test.androidXjUnit)
    androidTestImplementation(Dependencies.Test.androidXEspresso)
    androidTestImplementation(Dependencies.Test.conditionWatcher)
    androidTestImplementation(Dependencies.Test.espressoContrib)
    androidTestImplementation(Dependencies.Test.androidXTestRules)
    androidTestImplementation(Dependencies.Test.uiAutomator)
}