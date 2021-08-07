package com.example.amiiboapi.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AmiiboModelMinimal(
    val tail: String,
    val image: String,
    val name: String)
