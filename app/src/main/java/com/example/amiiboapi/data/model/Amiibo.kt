package com.example.amiiboapi.data.model

import com.squareup.moshi.JsonClass

/**
 * Контейнер для ответа с API
 *
 * @param T тип модели ответа
 * @property amiibo ответ с API
 *
 * @author Murad Luguev on 08-08-2021
 */
@JsonClass(generateAdapter = true)
data class Amiibo<T>(
    val amiibo: List<T>
)