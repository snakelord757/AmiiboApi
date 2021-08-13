package com.example.amiiboapi.domain.repository

import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel
import io.reactivex.Single

/**
 * Репозиторий для получения информации с API
 *
 * @author Murad Luguev on 08-08-2021
 */
interface AmiiboRepository {
    /**
     * Возвращает список игровых серий
     *
     * @return список [GameSeriesModel]
     */
    fun getGameSeries(): Amiibo<GameSeriesModel>

    /**
     * Возвращает предметы по заданной серии игр
     *
     * @param gameSeriesKey ключ серии игр
     * @return список [AmiiboModelMinimal]
     */
    fun getAmiiboByGameSeries(gameSeriesKey: String): Amiibo<AmiiboModelMinimal>

    /**
     * Возвращает информацию о конкретном предмете
     *
     * @param amiiboTail "хвост" предмета
     * @return список, сотсоящий из одного элемента [AmiiboModel]
     */
    fun getInfoAboutAmiibo(amiiboTail: String): Amiibo<AmiiboModel>
}