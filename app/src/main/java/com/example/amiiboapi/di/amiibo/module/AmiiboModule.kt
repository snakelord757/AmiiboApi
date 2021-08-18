package com.example.amiiboapi.di.amiibo.module

import com.example.amiiboapi.data.datasource.api.AmiiboApi
import com.example.amiiboapi.data.datasource.api.AmiiboApiImpl
import com.example.amiiboapi.data.datasource.storage.AmiiboStorage
import com.example.amiiboapi.data.datasource.storage.AmiiboStorageImpl
import com.example.amiiboapi.data.repository.AmiiboRepositoryImpl
import com.example.amiiboapi.domain.interactor.AmiiboInteractor
import com.example.amiiboapi.domain.interactor.AmiiboInteractorImpl
import com.example.amiiboapi.domain.mapper.ErrorMapper
import com.example.amiiboapi.domain.mapper.ErrorMapperImpl
import com.example.amiiboapi.domain.repository.AmiiboRepository
import dagger.Binds
import dagger.Module

@Module
interface AmiiboModule {
    @Binds
    fun bindsAmiiboApi(amiiboApi: AmiiboApiImpl): AmiiboApi

    @Binds
    fun bindsAmiiboStorage(amiiboStorageImpl: AmiiboStorageImpl): AmiiboStorage

    @Binds
    fun bindsAmiiboRepository(amiiboRepositoryImpl: AmiiboRepositoryImpl): AmiiboRepository

    @Binds
    fun bindsAmiiboInteractor(amiiboInteractorImpl: AmiiboInteractorImpl): AmiiboInteractor

    @Binds
    fun bindsErrorMapper(errorMapperImpl: ErrorMapperImpl): ErrorMapper
}