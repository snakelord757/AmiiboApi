package com.example.amiiboapi.di

import com.example.amiiboapi.data.datasource.AmiiboApi
import com.example.amiiboapi.data.datasource.AmiiboApiImpl
import com.example.amiiboapi.domain.repository.AmiiboRepository
import com.example.amiiboapi.data.repository.AmiiboRepositoryImpl
import com.example.amiiboapi.domain.interactor.AmiiboInteractor
import com.example.amiiboapi.domain.interactor.AmiiboInteractorImpl
import com.example.amiiboapi.domain.mapper.ErrorMapper
import com.example.amiiboapi.domain.mapper.ErrorMapperImpl
import com.example.amiiboapi.presentation.common.SchedulersProvider
import com.example.amiiboapi.presentation.common.SchedulersProviderImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Фейковая инъекция зависимостей
 *
 * @author Murad Luguev on 08-08-2021
 */
object FakeDependencyInjector {

    private const val COMMON_TIMEOUT = 5L

    private var api: AmiiboApi? = null
    private var repository: AmiiboRepository? = null
    private var interactor: AmiiboInteractor? = null
    private var provider: SchedulersProvider? = null
    private var errorMapper: ErrorMapper? = null

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

    /**
     * Возвращает подкготовленный интерактор
     *
     * @return экземпляр [AmiiboInteractor]
     */
    fun injectAmiiboInteractor(): AmiiboInteractor {
        if (interactor == null)
            interactor = AmiiboInteractorImpl(injectAmiiboRepository())
        return interactor!!
    }

    fun injectSchedulersProvider(): SchedulersProvider {
        if (provider == null)
            provider = SchedulersProviderImpl()
        return provider!!
    }

    fun injectErrorMapper(): ErrorMapper {
        if (errorMapper == null)
            errorMapper = ErrorMapperImpl()
        return errorMapper!!
    }
}