package com.example.amiiboapi.presentation.application

import android.app.Application
import com.example.amiiboapi.di.common.component.AppComponent
import com.example.amiiboapi.di.common.component.DaggerAppComponent

class AmiiboApiApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(applicationContext)
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }
}