package com.example.amiiboapi.di.common.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.amiiboapi.common.Const
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .callTimeout(COMMON_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(COMMON_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named(Const.AMIIBO_PREFERENCE_NAME)
    fun provideAmiiboSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(AMIIBO_PREFERENCES, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    @Named(Const.SETTINGS_PREFERENCES_NAME)
    fun provideSettingsSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    companion object {
        private const val COMMON_TIMEOUT = 5L
        private const val AMIIBO_PREFERENCES = "amiibo_preferences"
    }
}