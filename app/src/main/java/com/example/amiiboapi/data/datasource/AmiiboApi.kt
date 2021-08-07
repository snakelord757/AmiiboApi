package com.example.amiiboapi.data.datasource

import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel

interface AmiiboApi {
    fun getGameSeries(): Amiibo<GameSeriesModel>
    fun getAmiiboByGameSeries(gameSeriesKey: String): Amiibo<AmiiboModelMinimal>
    fun getInfoAboutAmiibo(amiiboTail: String): Amiibo<AmiiboModel>
}