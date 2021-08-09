package com.example.amiiboapi.domain

import com.example.amiiboapi.R
import com.example.amiiboapi.data.exception.BadResponseException
import com.example.amiiboapi.domain.mapper.ErrorMapperImpl
import com.example.amiiboapi.domain.model.Error
import com.google.common.truth.Truth
import io.mockk.mockk
import org.junit.Test
import java.io.InterruptedIOException
import java.net.UnknownHostException
import kotlin.random.Random

class ErrorMapperTest {

    private val errorMapper = ErrorMapperImpl()

    @Test
    fun testMapUnknownHostException() {
        //Arrange
        val unknownHostException = UnknownHostException()
        val expectedResult = EXPECTED_ERROR_UNKNOWN_HOST
        
        //Act
        val actualResult = errorMapper.mapError(unknownHostException)
        
        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }
    
    @Test
    fun testMapInterruptedIOException() {
        //Arrange
        val interruptedIOException = InterruptedIOException()
        val expectedResult = EXPECTED_ERROR_CONNECTION_TIMEOUT
        
        //Act
        val actualResult = errorMapper.mapError(interruptedIOException)
    
        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }
    
    @Test
    fun testMapBadResponseExceptionServerInternalError() {
        //Arrange
        val badResponseExceptionServerInternalError = BadResponseException(SERVER_INTERNAL_ERROR)
        val expectedResult = EXPECTED_ERROR_SERVER_INTERNAL_ERROR

        //Act
        val actualResult = errorMapper.mapError(badResponseExceptionServerInternalError)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun testMapBadResponseExceptionServerUnavailable() {
        //Arrange
        val badResponseExceptionServerUnavailable = BadResponseException(SERVER_UNAVAILABLE)
        val expectedResult = EXPECTED_ERROR_SERVER_UNAVAILABLE

        //Act
        val actualResult = errorMapper.mapError(badResponseExceptionServerUnavailable)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun testMapBadResponseExceptionNotFound() {
        //Arrange
        val badResponseExceptionNotFound = BadResponseException(NOT_FOUND)
        val expectedResult = EXPECTED_ERROR_NOT_FOUND

        //Act
        val actualResult = errorMapper.mapError(badResponseExceptionNotFound)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun testMapBadResponseExceptionRequestTimeout() {
        //Arrange
        val badResponseExceptionRequestTimeout = BadResponseException(REQUEST_TIMEOUT)
        val expectedResult = EXPECTED_ERROR_REQUEST_TIMEOUT

        //Act
        val actualResult = errorMapper.mapError(badResponseExceptionRequestTimeout)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun testMapUnhandledException() {
        //Arrange
        val randomException: Throwable = mockk()
        val expectedResult = EXPECTED_ERROR_UNKNOWN_ERROR

        //Act
        val actualResult = errorMapper.mapError(randomException)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun testMapBadResponseUnhandledErrorCode() {
        //Arrange
        val badResponseRandomCode = BadResponseException(Random.nextInt())
        val expectedResult = EXPECTED_ERROR_UNKNOWN_ERROR

        //Act
        val actualResult = errorMapper.mapError(badResponseRandomCode)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }
    
    companion object {
        private val EXPECTED_ERROR_UNKNOWN_HOST = Error(R.string.unknown_host_message)
        private val EXPECTED_ERROR_CONNECTION_TIMEOUT = Error(R.string.connection_timeout)

        private const val SERVER_INTERNAL_ERROR = 500
        private const val SERVER_UNAVAILABLE = 503
        private const val NOT_FOUND = 404
        private const val REQUEST_TIMEOUT = 408
        
        private val EXPECTED_ERROR_SERVER_INTERNAL_ERROR = Error(R.string.internal_server_error)
        private val EXPECTED_ERROR_SERVER_UNAVAILABLE = Error(R.string.server_unavailable)
        private val EXPECTED_ERROR_NOT_FOUND = Error(R.string.not_found)
        private val EXPECTED_ERROR_REQUEST_TIMEOUT = Error(R.string.request_timeout)
        private val EXPECTED_ERROR_UNKNOWN_ERROR = Error(R.string.unknown_error)
    }
}