package com.example.amiiboapi.data

import com.example.amiiboapi.data.datasource.AmiiboApi
import com.example.amiiboapi.data.datasource.AmiiboApiImpl
import com.example.amiiboapi.data.exception.BadResponseException
import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import okhttp3.OkHttpClient
import okhttp3.Response
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.lang.IllegalStateException

class AmiiboApiTest {

    private lateinit var amiiboApi: AmiiboApi
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var response: Response

    @Before
    fun setUp() {
        okHttpClient = mockk()
        response = mockk()
        every { okHttpClient.newCall(any()).execute() } returns response
        every { response.isSuccessful } returns true
        amiiboApi = AmiiboApiImpl(okHttpClient)
    }

    @Test
    fun testGetGameSeries() {
        //Arrange
        every { response.body!!.string() } returns GAME_SERIES_BODY
        val expectedResult = GAME_SERIES_RESPONSE

        //Act
        val actualResponse = amiiboApi.getGameSeries()

        //Assert
        Truth.assertThat(actualResponse).isEqualTo(expectedResult)
    }

    @Test
    fun getAmiiboByGameSeries() {
        //Arrange
        every { response.body!!.string() } returns AMIIBO_LIST_BODY
        val expectedResult = AMIIBO_LIST_EXPECTED_RESPONSE
        val gameSeriesKey = GAME_SERIES_KEY

        //Act
        val actualResult = amiiboApi.getAmiiboByGameSeries(gameSeriesKey)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun testGetInfoAboutAmiibo() {
        //Arrange
        every { response.body!!.string() } returns ABOUT_AMIIBO_BODY
        val expectedResult = AMIIBO_INFO_EXPECTED_RESPONSE
        val amiiboTail = AMIIBO_TAIL

        //Act
        val actualResult = amiiboApi.getInfoAboutAmiibo(amiiboTail)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test(expected = BadResponseException::class)
    fun testBadResponse() {
        //Arrange
        every { response.isSuccessful } returns false
        every { response.code } returns NOT_FOUND_CODE

        //Act
        amiiboApi.getGameSeries()
    }

    @Test(expected = IOException::class)
    fun testConnectionLost() {
        //Arrange
        val ioException = IOException(EXCEPTION_MESSAGE_IO)
        every { okHttpClient.newCall(any()).execute() } throws ioException

        //Act
        amiiboApi.getGameSeries()
    }

    @Test(expected = IllegalStateException::class)
    fun testCallAlreadyExecuted() {
        //Arrange
        val illegalStateException = IllegalStateException(EXCEPTION_MESSAGE_ILLEGAL_STATE)
        every { okHttpClient.newCall(any()).execute() } throws illegalStateException

        //Act
        amiiboApi.getGameSeries()
    }

    companion object {
        private const val GAME_SERIES_BODY = """{
	    "amiibo": [
		        {
			    "key": "0x000",
			    "name": "Super Mario"
		        }
            ]
        }"""

        private const val GAME_SERIES_KEY = "0x000"

        private const val AMIIBO_LIST_BODY = """{
	    "amiibo": [
		        {
			        "image": "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00000002.png",
			        "name": "Mario",
			        "tail": "00000002"
		        }
            ]
        }"""

        private const val AMIIBO_TAIL = "000e0002"

        private const val ABOUT_AMIIBO_BODY = """{
	    "amiibo": [
		        {
			        "amiiboSeries": "Super Smash Bros.",
			        "gameSeries": "The Legend of Zelda",
			        "image": "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01010000-000e0002.png",
			        "name": "Zelda",
			        "type": "Figure"
		        }
	        ]
        }"""

        private val GAME_SERIES_RESPONSE = Amiibo(listOf(GameSeriesModel(key = "0x000", "Super Mario")))

        private val AMIIBO_LIST_EXPECTED_RESPONSE = Amiibo(listOf(
            AmiiboModelMinimal(
                tail = "00000002",
                image = "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00000002.png",
                name = "Mario")
        ))

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
