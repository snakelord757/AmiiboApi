package com.example.amiiboapi.domain.mapper

import com.example.amiiboapi.domain.model.Error

interface ErrorMapper {
    fun mapError(throwable: Throwable): Error
}