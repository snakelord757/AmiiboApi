package com.example.amiiboapi.data.model

import com.squareup.moshi.JsonClass

/**
 * Модель для отображения игровой серии
 *
 * @property key ключ серии, по которому получаем предметы по заданной серии игр
 * @property name название серии
 *
 * @author Murad Luguev on 08-08-2021
 */
@JsonClass(generateAdapter = true)
data class GameSeriesModel(
    val key: String,
    val name: String
)
