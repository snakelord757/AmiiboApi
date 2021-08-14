package com.example.amiiboapi.data.datasource.storage

import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel

/**
 * Контракт, описывающий поведение локального хранилища
 *
 * @author Luguev Murad on 13-08-2021
 */
interface AmiiboStorage {

    /**
     * Получение списка игровых серий из локального хранилища
     *
     * @param key ключ, по которому хранится значение
     * @return nullable контейнер [Amiibo], который содержит список [GameSeriesModel]
     */
    fun getGameSeriesFromStorage(key: String): Amiibo<GameSeriesModel>?

    /**
     * Получение списка фигурок/карточек/ярнов по игровой серии из локального хранилища
     *
     * @param gameSeriesKey ключ, по которому хранится значение
     * @return nullable контейнер [Amiibo], который содержит список [AmiiboModelMinimal]
     */
    fun getAmiiboByGameSeriesFromStorage(gameSeriesKey: String): Amiibo<AmiiboModelMinimal>?

    /**
     * Получениеи информации о фигурке/карточке/ярне
     *
     * @param amiiboTailKey ключ, по которому хранится значение
     * @return nullable контейнер [Amiibo], который содержит список [GameSeriesModel]
     */
    fun getInfoAboutAmiiboFromStorage(amiiboTailKey: String): Amiibo<AmiiboModel>?

    /**
     * Сохранение информации в локальное хранилще
     *
     * @param key ключ, по которому можно найти значение в будущем
     * @param value значение, которое необходимо сохранить
     */
    fun insertIntoStorage(key: String, value: String)
}