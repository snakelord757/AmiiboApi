package com.example.amiiboapi.data.mapper

import com.example.amiiboapi.data.model.Amiibo
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object ResponseMapper {
    inline fun <reified T> mapResponse(input: String): Amiibo<T> {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(Amiibo::class.java, T::class.java)
        val jsonAdapter: JsonAdapter<Amiibo<T>> = moshi.adapter(type)
        return jsonAdapter.fromJson(input)!!
    }
}