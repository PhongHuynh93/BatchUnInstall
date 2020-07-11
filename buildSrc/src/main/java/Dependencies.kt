object Versions {
    // kotlin
    const val kotlin = "1.3.61"
    const val kotlinCoroutines = "1.3.3"
    const val gradlePlugin: String = "4.0.0"
    const val glide: String = "4.10.0"
    const val supportLibrary: String = "1.1.0"
    const val material: String = "1.1.0"
    const val constraintLayout: String = "2.0.0-beta4"
    const val nav: String = "2.2.0"
    const val glidePalette: String = "2.1.2"
    const val room: String = "2.2.2"
}

object Libs {
    object Android {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.supportLibrary}"
        const val material = "com.google.android.material:material:${Versions.material}"
        const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.supportLibrary}"
        const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val viewPager = "androidx.viewpager2:viewpager2:1.0.0"
    }

    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val stdLib2 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    }

    object Dagger {
        const val core = "com.google.dagger:hilt-android:2.28.1-alpha"
        const val compiler = "com.google.dagger:hilt-android-compiler:2.28-alpha"
        const val viewmodel =  "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha01"
        const val viewmodelCompiler = "androidx.hilt:hilt-compiler:1.0.0-alpha01"
    }

    object Test {
        const val junit = "junit:junit:4.12"
        const val runner = "com.android.support.test:runner:1.0.2"
        const val espresso = "com.android.support.test.espresso:espresso-core:3.0.2"
    }

    object Glide {
        const val glide1 = "com.github.bumptech.glide:glide:${Versions.glide}"
        const val glide2 = "com.github.bumptech.glide:compiler:${Versions.glide}"
        const val glideTransform = "jp.wasabeef:glide-transformations:4.0.0"
    }

    object Ktx {
        const val ktxCore = "androidx.core:core-ktx:1.3.0"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.0-alpha06"
        const val lifeCycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
        const val nav1 = "androidx.navigation:navigation-fragment-ktx:${Versions.nav}"
        const val nav2 = "androidx.navigation:navigation-ui-ktx:${Versions.nav}"
    }

    object Helper {
        const val inlineActivityResult = "com.afollestad.inline-activity-result:core:0.2.0"
        const val inlinePermission = "com.afollestad.assent:core:3.0.0-RC4"
        const val timber = "com.jakewharton.timber:timber:4.7.1"
        const val lottie = "com.airbnb.android:lottie:3.0.1"
        const val gson = "com.google.code.gson:gson:2.8.6"
        const val stetho = "com.facebook.stetho:stetho:1.5.1"
        const val multidex = "androidx.multidex:multidex:2.0.1"
        const val playCore = "com.google.android.play:core:1.6.4"
//        const val firebase = "com.google.firebase:firebase-analytics:17.4.3"
//        const val crashLytic = "com.google.firebase:firebase-crashlytics:17.1.0"
        const val fbShare = "com.facebook.android:facebook-share:4.40.0"
        const val dialog = "com.afollestad.material-dialogs:core:3.3.0"
    }

    object Thread {
        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    }
    object Db {
        // Room with RXjava2 & Kotlin
        const val room = "androidx.room:room-runtime:${Versions.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
        const val roomRx2 = "androidx.room:room-ktx:${Versions.room}"
    }
    object Ad {
        const val adMob = "com.google.android.gms:play-services-ads:19.2.0"
    }
}

object Configs {
    const val minSdk = 16
    const val compileSdk = 28
    const val targetSdk = 28

    const val applicationId = "com.wind.batchuninstall"
    const val versionCode = 1
    const val versionName = "0.0.1"
}

object Plugins {
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "android"
    const val kotlinExtensions = "android.extensions"
//    const val googleService = "com.google.gms.google-services"
//    const val crashlytics = "com.google.firebase.crashlytics"
    const val kapt = "kapt"
    const val hilt = "dagger.hilt.android.plugin"
}

object ClassPaths {
    const val gradlePlugin = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.nav}"
    const val googleServices = "com.google.gms:google-services:4.3.3"
    const val crashlytics = "com.google.firebase:firebase-crashlytics-gradle:2.0.0-beta02"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:2.28-alpha"
}