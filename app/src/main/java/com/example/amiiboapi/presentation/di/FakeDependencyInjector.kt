package com.example.amiiboapi.presentation.di

import com.example.amiiboapi.data.datasource.AmiiboApi
import com.example.amiiboapi.data.datasource.AmiiboApiImpl
import com.example.amiiboapi.data.repository.AmiiboRepository
import com.example.amiiboapi.data.repository.AmiiboRepositoryImpl
import com.example.amiiboapi.domain.interactor.AmiiboInteractor
import com.example.amiiboapi.domain.interactor.AmiiboInteractorImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object FakeDependencyInjector {

    private const val COMMON_TIMEOUT = 5L

    private var api: AmiiboApi? = null
    private var repository: AmiiboRepository? = null
    private var interactor: AmiiboInteractor? = null

    private fun injectAmiiboApi(): AmiiboApi {
        if (api == null) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            api = AmiiboApiImpl(OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .callTimeout(COMMON_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(COMMON_TIMEOUT, TimeUnit.SECONDS)
                .build())
        }
        return api!!
    }

    private fun injectAmiiboRepository(): AmiiboRepository {
        if (repository == null) {
            repository = AmiiboRepositoryImpl(injectAmiiboApi())
        }
        return repository!!
    }

    fun injectAmiiboInteractor(): AmiiboInteractor {
        if (interactor == null)
            interactor = AmiiboInteractorImpl(injectAmiiboRepository())
        return interactor!!
    }
}