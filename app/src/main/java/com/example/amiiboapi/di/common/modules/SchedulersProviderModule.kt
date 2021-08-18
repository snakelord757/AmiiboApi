package com.example.amiiboapi.di.common.modules

import com.example.amiiboapi.presentation.common.SchedulersProvider
import com.example.amiiboapi.presentation.common.SchedulersProviderImpl
import dagger.Binds
import dagger.Module

@Module
interface SchedulersProviderModule {
    @Binds
    fun bindsSchedulerProvider(schedulersProviderImpl: SchedulersProviderImpl): SchedulersProvider
}