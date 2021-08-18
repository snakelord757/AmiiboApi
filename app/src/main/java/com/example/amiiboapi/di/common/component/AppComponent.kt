package com.example.amiiboapi.di.common.component

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import com.example.amiiboapi.common.Const
import com.example.amiiboapi.di.common.modules.AppModule
import com.example.amiiboapi.di.common.modules.SchedulersProviderModule
import com.example.amiiboapi.di.common.modules.ViewModelFactoryModule
import com.example.amiiboapi.presentation.common.SchedulersProvider
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ViewModelFactoryModule::class,
    SchedulersProviderModule::class]
)
interface AppComponent {

    fun okHttpClient(): OkHttpClient

    @Named(Const.AMIIBO_PREFERENCE_NAME)
    fun amiiboSharedPreferences(): SharedPreferences

    @Named(Const.SETTINGS_PREFERENCES_NAME)
    fun settingsSharedPreferences(): SharedPreferences

    fun viewModelFactory(): ViewModelProvider.Factory

    fun schedulersProvider(): SchedulersProvider

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}