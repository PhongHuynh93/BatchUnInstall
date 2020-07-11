package com.wind.batchuninstall

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Created by Phong Huynh on 7/11/2020.
 */
@InstallIn(ApplicationComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun providePackageManager(@ApplicationContext context: Context) = context.packageManager
}