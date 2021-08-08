package com.example.amiiboapi.data.datasource

import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel

/**
 * Контракт, описывающий то, что должно вернуть API
 *
 * @author Murad Luguev on 08-08-2021
 */
interface AmiiboApi {
    /**
     * Получение списка игровых серий
     *
     * @return список [GameSeriesModel]
     */
    fun getGameSeries(): Amiibo<GameSeriesModel>

    /**
     * Получение списка предетов по игровой серии
     *
     * @param gameSeriesKey ключ игровой серии
     * @return список [AmiiboModelMinimal]
     */
    fun getAmiiboByGameSeries(gameSeriesKey: String): Amiibo<AmiiboModelMinimal>

    /**
     * Получение подрообной информации о предмете
     *
     * @param amiiboTail "хвост" предмета
     * @return список, сотсоящий из одного элемента [AmiiboModel]
     */
    fun getInfoAboutAmiibo(amiiboTail: String): Amiibo<AmiiboModel>
}