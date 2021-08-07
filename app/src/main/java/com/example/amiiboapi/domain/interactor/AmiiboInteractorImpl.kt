package com.example.amiiboapi.domain.interactor

import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel
import com.example.amiiboapi.data.repository.AmiiboRepository

class AmiiboInteractorImpl(
    private val amiiboRepository: AmiiboRepository
) : AmiiboInteractor {

    override fun getGameSeries(): Amiibo<GameSeriesModel> {
        return amiiboRepository.getGameSeries()
    }

    override fun getAmiiboByGameSeries(gameSeriesKey: String): Amiibo<AmiiboModelMinimal> {
        return amiiboRepository.getAmiiboByGameSeries(gameSeriesKey)
    }

    override fun getInfoAboutAmiibo(amiiboTail: String): Amiibo<AmiiboModel> {
        return amiiboRepository.getInfoAboutAmiibo(amiiboTail)
    }
}