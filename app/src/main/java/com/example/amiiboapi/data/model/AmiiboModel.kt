package com.example.amiiboapi.data.model

import com.squareup.moshi.JsonClass

/**
 * Модель, описывающая Amiibo
 *
 * @property amiiboSeries серия, которой принадлежит предмет
 * @property image изображение предмета
 * @property name название предмета
 * @property gameSeries игровая серия, которой принадлежит предмет
 * @property type тип предмета
 *
 * @author Murad Luguev on 08-08-2021
 */
@JsonClass(generateAdapter = true)
data class AmiiboModel(
    val amiiboSeries: String,
    val image: String,
    val name: String,
    val gameSeries: String,
    val type: String
)
