package com.example.amiiboapi.domain.interactor

import androidx.annotation.WorkerThread
import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Интерактор для получения информации из репозитория
 *
 *
 * @author Murad Luguev on 08-08-2021
 */
interface AmiiboInteractor {
    /**
     * Возвращает список игровых серий
     *
     * @return список [GameSeriesModel]
     */
    @WorkerThread
    fun getGameSeries(forceReload: Boolean): Amiibo<GameSeriesModel>

    /**
     * Возвращает предметы по заданной серии игр
     *
     * @param gameSeriesKey ключ серии игр
     * @return список [AmiiboModelMinimal]
     */
    @WorkerThread
    fun getAmiiboByGameSeries(gameSeriesKey: String, forceReload: Boolean): Amiibo<AmiiboModelMinimal>

    /**
     * Возвращает информацию о конкретном предмете
     *
     * @param amiiboTail "хвост" предмета
     * @return список, сотсоящий из одного элемента [AmiiboModel]
     */
    @WorkerThread
    fun getInfoAboutAmiibo(amiiboTail: String, forceReload: Boolean): Amiibo<AmiiboModel>
}