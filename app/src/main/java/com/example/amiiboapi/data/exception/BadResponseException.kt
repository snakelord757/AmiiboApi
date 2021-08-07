package com.example.amiiboapi.data.exception

class BadResponseException(val responseCode: Int): Throwable()