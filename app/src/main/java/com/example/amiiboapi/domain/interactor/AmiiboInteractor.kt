package com.example.amiiboapi.domain.interactor

import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel
import io.reactivex.Single

interface AmiiboInteractor {
    fun getGameSeries(): Amiibo<GameSeriesModel>
    fun getAmiiboByGameSeries(gameSeriesKey: String): Amiibo<AmiiboModelMinimal>
    fun getInfoAboutAmiibo(amiiboTail: String): Amiibo<AmiiboModel>
}