package com.example.amiiboapi.domain.interactor

import androidx.annotation.WorkerThread
import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel
import com.example.amiiboapi.domain.repository.AmiiboRepository

class AmiiboInteractorImpl(
    private val amiiboRepository: AmiiboRepository
) : AmiiboInteractor {

    @WorkerThread
    override fun getGameSeries(): Amiibo<GameSeriesModel> {
        return amiiboRepository.getGameSeries()
    }

    @WorkerThread
    override fun getAmiiboByGameSeries(gameSeriesKey: String): Amiibo<AmiiboModelMinimal> {
        return amiiboRepository.getAmiiboByGameSeries(gameSeriesKey)
    }

    @WorkerThread
    override fun getInfoAboutAmiibo(amiiboTail: String): Amiibo<AmiiboModel> {
        return amiiboRepository.getInfoAboutAmiibo(amiiboTail)
    }
}