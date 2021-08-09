package com.example.amiiboapi.domain

import com.example.amiiboapi.data.exception.BadResponseException
import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel
import com.example.amiiboapi.data.repository.AmiiboRepository
import com.example.amiiboapi.domain.interactor.AmiiboInteractor
import com.example.amiiboapi.domain.interactor.AmiiboInteractorImpl
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import java.io.IOException
import java.lang.IllegalStateException

class AmiiboInteractorTest {
    
    private val amiiboRepository: AmiiboRepository = mockk()
    private val amiiboInteractor: AmiiboInteractor = AmiiboInteractorImpl(amiiboRepository)

    @Test
    fun testGetGameSeries() {
        //Arrange
        every { amiiboRepository.getGameSeries() } returns GAME_SERIES_RESPONSE
        val expectedResult = GAME_SERIES_RESPONSE

        //Act
        val actualResult = amiiboInteractor.getGameSeries()

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun testGetAmiiboByGameSeries() {
        //Arrange
        every { amiiboRepository.getAmiiboByGameSeries(GAME_SERIES_KEY) } returns AMIIBO_LIST_EXPECTED_RESPONSE
        val expectedResult = AMIIBO_LIST_EXPECTED_RESPONSE

        //Act
        val actualResult = amiiboInteractor.getAmiiboByGameSeries(GAME_SERIES_KEY)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun testGetInfoAboutAmiibo() {
        //Arrange
        every { amiiboRepository.getInfoAboutAmiibo(AMIIBO_TAIL) } returns AMIIBO_INFO_EXPECTED_RESPONSE
        val expectedResult = AMIIBO_INFO_EXPECTED_RESPONSE

        //Act
        val actualResult = amiiboInteractor.getInfoAboutAmiibo(AMIIBO_TAIL)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test(expected = BadResponseException::class)
    fun testBadResponse() {
        //Arrange
        every { amiiboRepository.getGameSeries() } throws BadResponseException(NOT_FOUND_CODE)

        //Act
        amiiboInteractor.getGameSeries()
    }

    @Test(expected = IOException::class)
    fun testConnectionLost() {
        //Arrange
        val ioException = IOException(EXCEPTION_MESSAGE_IO)
        every { amiiboRepository.getGameSeries() } throws ioException

        //Act
        amiiboInteractor.getGameSeries()
    }

    @Test(expected = IllegalStateException::class)
    fun testCallAlreadyExecuted() {
        //Arrange
        val illegalStateException = IllegalStateException(EXCEPTION_MESSAGE_ILLEGAL_STATE)
        every { amiiboRepository.getGameSeries()} throws illegalStateException

        //Act
        amiiboInteractor.getGameSeries()
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
    }
}