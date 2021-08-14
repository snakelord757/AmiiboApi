package com.example.amiiboapi.data

import com.example.amiiboapi.data.datasource.api.AmiiboApi
import com.example.amiiboapi.data.datasource.storage.AmiiboStorage
import com.example.amiiboapi.data.exception.BadResponseException
import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel
import com.example.amiiboapi.domain.repository.AmiiboRepository
import com.example.amiiboapi.data.repository.AmiiboRepositoryImpl
import com.google.common.truth.Truth
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import java.io.IOException
import java.lang.IllegalStateException

class AmiiboRepositoryTest {

    private val amiiboApi: AmiiboApi = mockk()
    private val amiiboStorage: AmiiboStorage = mockk(relaxed = true)
    private val amiiboRepository: AmiiboRepository = AmiiboRepositoryImpl(amiiboApi, amiiboStorage)

    @Test
    fun testGetGameSeries() {
        //Arrange
        every { amiiboApi.getGameSeries() } returns GAME_SERIES_RESPONSE

        val expectedResult = GAME_SERIES_RESPONSE

        //Act
        val actualResult = amiiboRepository.getGameSeries(true)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun testGetAmiiboByGameSeries() {
        //Arrange
        every { amiiboApi.getAmiiboByGameSeries(GAME_SERIES_KEY) } returns AMIIBO_LIST_EXPECTED_RESPONSE
        val expectedResult = AMIIBO_LIST_EXPECTED_RESPONSE

        //Act
        val actualResult = amiiboRepository.getAmiiboByGameSeries(GAME_SERIES_KEY, true)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun testGetInfoAboutAmiibo() {
        //Arrange
        every { amiiboApi.getInfoAboutAmiibo(AMIIBO_TAIL) } returns AMIIBO_INFO_EXPECTED_RESPONSE
        val expectedResult = AMIIBO_INFO_EXPECTED_RESPONSE

        //Act
        val actualResult = amiiboRepository.getInfoAboutAmiibo(AMIIBO_TAIL, true)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun getGameSeriesFromStorage() {
        //Arrange
        every { amiiboStorage.getGameSeriesFromStorage(GAME_SERIES_PREFERENCES_KEY) } returns GAME_SERIES_RESPONSE
        val expectedResult = GAME_SERIES_RESPONSE

        //Act
        val actualResult = amiiboRepository.getGameSeries(false)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
        verify { amiiboApi.getGameSeries() wasNot Called}
    }

    @Test
    fun getAmiiboByGameSeriesFromStorage() {
        //Arrange
        every { amiiboStorage.getAmiiboByGameSeriesFromStorage(GAME_SERIES_KEY) } returns AMIIBO_LIST_EXPECTED_RESPONSE
        val expectedResult = AMIIBO_LIST_EXPECTED_RESPONSE

        //Act
        val actualResult = amiiboRepository.getAmiiboByGameSeries(GAME_SERIES_KEY, false)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
        verify { amiiboApi.getAmiiboByGameSeries(GAME_SERIES_KEY) wasNot Called}
    }

    @Test
    fun testGetInfoAboutAmiiboFromStorage() {
        //Arrange
        every { amiiboStorage.getInfoAboutAmiiboFromStorage(AMIIBO_TAIL) } returns AMIIBO_INFO_EXPECTED_RESPONSE
        val expectedResult = AMIIBO_INFO_EXPECTED_RESPONSE

        //Act
        val actualResult = amiiboRepository.getInfoAboutAmiibo(AMIIBO_TAIL, false)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
        verify { amiiboApi.getInfoAboutAmiibo(AMIIBO_TAIL) wasNot Called}
    }

    @Test
    fun testPreferencesEmpty() {
        //Arrange
        every { amiiboStorage.getGameSeriesFromStorage(GAME_SERIES_PREFERENCES_KEY) } returns null
        every { amiiboApi.getGameSeries() } returns GAME_SERIES_RESPONSE
        val expectedResult = GAME_SERIES_RESPONSE

        //Act
        val actualResult = amiiboRepository.getGameSeries(false)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test(expected = BadResponseException::class)
    fun testBadResponse() {
        //Arrange
        every { amiiboApi.getGameSeries() } throws BadResponseException(NOT_FOUND_CODE)

        //Act
        amiiboRepository.getGameSeries(true)
    }

    @Test(expected = IOException::class)
    fun testConnectionLost() {
        //Arrange
        val ioException = IOException(EXCEPTION_MESSAGE_IO)
        every { amiiboApi.getGameSeries() } throws ioException

        //Act
        amiiboApi.getGameSeries()
    }

    @Test(expected = IllegalStateException::class)
    fun testCallAlreadyExecuted() {
        //Arrange
        val illegalStateException = IllegalStateException(EXCEPTION_MESSAGE_ILLEGAL_STATE)
        every { amiiboApi.getGameSeries() } throws illegalStateException

        //Act
        amiiboApi.getGameSeries()
    }

    companion object {
        private val GAME_SERIES_RESPONSE = Amiibo(listOf(GameSeriesModel(key = "0x000", "Super Mario")))

        private const val GAME_SERIES_KEY = "0x000"

        private val AMIIBO_LIST_EXPECTED_RESPONSE = Amiibo(listOf(
            AmiiboModelMinimal(
                tail = "00000002",
                image = "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00000002.png",
                name = "Mario")
        ))

        private const val AMIIBO_TAIL = "000e0002"

        private val AMIIBO_INFO_EXPECTED_RESPONSE = Amiibo(listOf(
            AmiiboModel(
                amiiboSeries = "Super Smash Bros.",
                gameSeries = "The Legend of Zelda",
                image = "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01010000-000e0002.png",
                name = "Zelda",
                type = "Figure")
        ))

        private const val NOT_FOUND_CODE = 404

        private const val EXCEPTION_MESSAGE_IO = "Unable to resolve connection"

        private const val EXCEPTION_MESSAGE_ILLEGAL_STATE = "Call already executed"

        private const val GAME_SERIES_PREFERENCES_KEY = "game_series"
    }
}