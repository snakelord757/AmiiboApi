package com.example.amiiboapi.data.datasource.api

import com.example.amiiboapi.data.exception.BadResponseException
import com.example.amiiboapi.data.mapper.ResponseMapper
import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel
import okhttp3.*
import java.io.IOException
import java.lang.IllegalStateException
import javax.inject.Inject
import kotlin.jvm.Throws

class AmiiboApiImpl @Inject constructor(
    private val okHttpClient: OkHttpClient
) : AmiiboApi {

    override fun getGameSeries(): Amiibo<GameSeriesModel> {
        val response = executeRequest(getRequest(AMIIBO_GAME_SERIES_URL))
        return ResponseMapper.mapResponse(response.body!!.string())
    }

    override fun getAmiiboByGameSeries(gameSeriesKey: String): Amiibo<AmiiboModelMinimal> {
        val response = executeRequest(getRequest(AMIIBO_BY_GAME_SERIES_REQUEST_URL + gameSeriesKey))
        return ResponseMapper.mapResponse(response.body!!.string())
    }

    override fun getInfoAboutAmiibo(amiiboTail: String): Amiibo<AmiiboModel> {
        val response = executeRequest(getRequest(AMIIBO_FULL_INFORMATION_REQUEST_URL + amiiboTail))
        return ResponseMapper.mapResponse(response.body!!.string())
    }

    @Throws(IOException::class, IllegalStateException::class, BadResponseException::class)
    private fun executeRequest(request: Request): Response {
        val response = okHttpClient
            .newCall(request)
            .execute()
        if (response.isSuccessful)
            return response
        throw BadResponseException(response.code)
    }

    private fun getRequest(url: String) =
        Request.Builder()
            .url(url)
            .get()
            .build()

    companion object {
        private const val AMIIBO_GAME_SERIES_URL = "https://www.amiiboapi.com/api/gameseries"
        private const val AMIIBO_BY_GAME_SERIES_REQUEST_URL = "https://www.amiiboapi.com/api/amiibo/?gameseries="
        private const val AMIIBO_FULL_INFORMATION_REQUEST_URL = "https://www.amiiboapi.com/api/amiibo/?tail="
    }
}