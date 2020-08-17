package be.chaidev.chronote.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import be.chaidev.chronote.util.GlideManager
import be.chaidev.chronote.util.GlideRequestManager
import be.chaidev.chronote.util.SharedPreferenceKeys
import com.bumptech.glide.Glide
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences(SharedPreferenceKeys.APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSharedPrefsEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    @Singleton
    @Provides
    fun provideGlideRequestManager(
        application: Application
    ): GlideManager {
        return GlideRequestManager(
            Glide.with(application)
        )
    }
}