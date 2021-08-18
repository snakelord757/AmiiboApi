package com.example.amiiboapi.domain.interactor

import androidx.annotation.WorkerThread
import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel
import com.example.amiiboapi.domain.repository.AmiiboRepository
import javax.inject.Inject

class AmiiboInteractorImpl @Inject constructor(
    private val amiiboRepository: AmiiboRepository
) : AmiiboInteractor {

    @WorkerThread
    override fun getGameSeries(forceReload: Boolean): Amiibo<GameSeriesModel> {
        return amiiboRepository.getGameSeries(forceReload)
    }

    @WorkerThread
    override fun getAmiiboByGameSeries(gameSeriesKey: String, forceReload: Boolean): Amiibo<AmiiboModelMinimal> {
        return amiiboRepository.getAmiiboByGameSeries(gameSeriesKey, forceReload)
    }

    @WorkerThread
    override fun getInfoAboutAmiibo(amiiboTail: String, forceReload: Boolean): Amiibo<AmiiboModel> {
        return amiiboRepository.getInfoAboutAmiibo(amiiboTail, forceReload)
    }
}