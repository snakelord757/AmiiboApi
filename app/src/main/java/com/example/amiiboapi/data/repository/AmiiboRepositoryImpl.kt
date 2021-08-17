package com.example.amiiboapi.data.repository

import com.example.amiiboapi.data.datasource.api.AmiiboApi
import com.example.amiiboapi.data.datasource.storage.AmiiboStorage
import com.example.amiiboapi.data.mapper.ResponseMapper
import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel
import com.example.amiiboapi.domain.repository.AmiiboRepository

class AmiiboRepositoryImpl(
    private val amiiboApi: AmiiboApi,
    private val amiiboStorage: AmiiboStorage
) : AmiiboRepository {

    override fun getGameSeries(forceReload: Boolean): Amiibo<GameSeriesModel> {
        return if (forceReload) {
            loadFromApi { amiiboApi.getGameSeries() }
        } else {
            return amiiboStorage.getGameSeriesFromStorage(GAME_SERIES_KEY)
                ?: loadFromApi { amiiboApi.getGameSeries() }
        }
    }

    override fun getAmiiboByGameSeries(gameSeriesKey: String, forceReload: Boolean): Amiibo<AmiiboModelMinimal> {
        return if (forceReload) {
            loadFromApi(gameSeriesKey) { amiiboApi.getAmiiboByGameSeries(gameSeriesKey) }
        }
        else {
            return amiiboStorage.getAmiiboByGameSeriesFromStorage(gameSeriesKey)
                ?: loadFromApi(gameSeriesKey) { amiiboApi.getAmiiboByGameSeries(gameSeriesKey) }
        }
    }

    override fun getInfoAboutAmiibo(amiiboTail: String, forceReload: Boolean): Amiibo<AmiiboModel> {
        return if (forceReload) {
            loadFromApi(amiiboTail) { amiiboApi.getInfoAboutAmiibo(amiiboTail) }
        }
        else {
            return amiiboStorage.getInfoAboutAmiiboFromStorage(amiiboTail)
                ?: loadFromApi(amiiboTail) { amiiboApi.getInfoAboutAmiibo(amiiboTail) }
        }
    }

    private inline fun <reified T> loadFromApi(
        key: String = GAME_SERIES_KEY,
        apiCall: () -> Amiibo<T>
    ): Amiibo<T> {
        val apiResponse = apiCall.invoke()
        if (amiiboStorage.getForceStoreParameter(FORCE_STORE_PARAMETER_KEY)) {
            insertIntoStorage(key, apiResponse)
        }
        return apiResponse
    }

    private inline fun <reified T> insertIntoStorage(key: String, response: Amiibo<T>) {
        val responseJson = ResponseMapper.mapToJson(response)
        amiiboStorage.insertIntoStorage(key, responseJson)
    }

    companion object {
        private const val FORCE_STORE_PARAMETER_KEY = "force_store"
        private const val GAME_SERIES_KEY = "game_series"
    }
}