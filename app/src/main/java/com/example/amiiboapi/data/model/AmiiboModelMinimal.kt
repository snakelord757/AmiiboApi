package com.example.amiiboapi.data.model

import com.squareup.moshi.JsonClass

/**
 * Модель, для отображения в списке
 * Содержит минимальную информацию о предмете
 *
 * @property tail "хвост" предмета, для получения подробной информации о нём
 * @property image изображение предмета
 * @property name название предмета
 *
 * @author Murad Luguev on 08-08-2021
 */
@JsonClass(generateAdapter = true)
data class AmiiboModelMinimal(
    val tail: String,
    val image: String,
    val name: String)
