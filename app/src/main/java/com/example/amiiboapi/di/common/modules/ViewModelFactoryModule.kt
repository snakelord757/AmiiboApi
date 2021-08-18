package com.example.amiiboapi.di.common.modules

import androidx.lifecycle.ViewModelProvider
import com.example.amiiboapi.di.common.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {
    @Binds
    fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}