package com.example.amiiboapi.data.repository

import com.example.amiiboapi.data.datasource.AmiiboApi
import com.example.amiiboapi.data.datasource.AmiiboApiImpl
import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel
import io.reactivex.Single

class AmiiboRepositoryImpl(
    private val amiiboApi: AmiiboApi
) : AmiiboRepository {

    override fun getGameSeries(): Amiibo<GameSeriesModel> {
        return amiiboApi.getGameSeries()
    }

    override fun getAmiiboByGameSeries(gameSeriesKey: String): Amiibo<AmiiboModelMinimal> {
        return amiiboApi.getAmiiboByGameSeries(gameSeriesKey)
    }

    override fun getInfoAboutAmiibo(amiiboTail: String): Amiibo<AmiiboModel> {
        return amiiboApi.getInfoAboutAmiibo(amiiboTail)
    }
}