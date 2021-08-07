package com.example.amiiboapi.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Amiibo<T>(
    val amiibo: List<T>
)