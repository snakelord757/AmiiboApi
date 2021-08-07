package com.example.amiiboapi.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameSeriesModel(
    val key: String,
    val name: String
)
