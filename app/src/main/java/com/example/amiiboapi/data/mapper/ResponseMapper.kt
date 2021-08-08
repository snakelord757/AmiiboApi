package com.example.amiiboapi.data.mapper

import com.example.amiiboapi.data.model.Amiibo
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * Маппер ответа с API
 * Преобразует JSON в Amiibo<T>,
 * где T - тип содержимого контейнера [Amiibo]
 *
 * @author Murad Luguev on 08-08-2021
 */
object ResponseMapper {

    /**
     * Преобразование ответа из [input] в [Amiibo]
     *
     * @param T тип содержимого контейнера
     * @param input json, который необходимо преобразовать
     * @return контейнер [Amiibo], который содержит ответ
     */
    inline fun <reified T> mapResponse(input: String): Amiibo<T> {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(Amiibo::class.java, T::class.java)
        val jsonAdapter: JsonAdapter<Amiibo<T>> = moshi.adapter(type)
        return jsonAdapter.fromJson(input)!!
    }
}