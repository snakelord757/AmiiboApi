package com.example.amiiboapi.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AmiiboModel(
    val amiiboSeries: String,
    val image: String,
    val name: String,
    val gameSeries: String,
    val type: String
)
