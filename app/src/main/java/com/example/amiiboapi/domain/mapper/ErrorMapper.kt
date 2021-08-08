package com.example.amiiboapi.domain.mapper

import com.example.amiiboapi.R
import com.example.amiiboapi.data.exception.BadResponseException
import com.example.amiiboapi.domain.model.Error
import java.io.InterruptedIOException
import java.net.UnknownHostException

/**
 * Маппер ошибок, которые могут прийти во время получения ответа
 *
 * @author Murad Luguev on 08-08-2021
 */
object ErrorMapper {

    private const val SERVER_INTERNAL_ERROR = 500
    private const val SERVER_UNAVAILABLE = 503
    private const val NOT_FOUND = 404
    private const val REQUEST_TIMEOUT = 408

    /**
     * Возвращает [Error], котоырй содержит информцаию об ошибке
     *
     * @param throwable исключение, которое возникло во время получения ответа
     *
     * @return [Error] с описанием ошибки
     */
    fun mapError(throwable: Throwable): Error =
        when (throwable) {
            is UnknownHostException -> Error(R.string.unknown_host_message)
            is InterruptedIOException -> Error(R.string.connection_timeout)
            is BadResponseException -> matchError(throwable.responseCode)
            else -> Error(R.string.unknown_error)
        }

    private fun matchError(responseCode: Int): Error {
        return when (responseCode) {
            SERVER_INTERNAL_ERROR -> Error(R.string.internal_server_error)
            SERVER_UNAVAILABLE -> Error(R.string.server_unavailable)
            NOT_FOUND -> Error(R.string.not_found)
            REQUEST_TIMEOUT -> Error(R.string.request_timeout)
            else -> Error(R.string.unknown_error)
        }
    }
}