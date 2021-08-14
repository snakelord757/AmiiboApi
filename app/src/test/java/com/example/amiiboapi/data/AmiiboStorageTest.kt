package com.example.amiiboapi.data

import android.content.SharedPreferences
import com.example.amiiboapi.data.datasource.storage.AmiiboStorage
import com.example.amiiboapi.data.datasource.storage.AmiiboStorageImpl
import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class AmiiboStorageTest {

    private val sharedPreferences: SharedPreferences = mockk()
    private val amiiboStorage: AmiiboStorage = AmiiboStorageImpl(sharedPreferences)

    @Test
    fun testStorageIsEmpty() {
        //Arrange
        every { sharedPreferences.getString(any(), any()) } returns null
        val expectedResult = null

        //Act
        val gameSeriesActualResult = amiiboStorage.getGameSeriesFromStorage(GAME_SERIES_PREFERENCES_KEY)
        val amiiboByGameSeriesActualResult = amiiboStorage.getAmiiboByGameSeriesFromStorage(GAME_SERIES_KEY)
        val infoAboutAmiiboActualResult = amiiboStorage.getInfoAboutAmiiboFromStorage(AMIIBO_TAIL)

        //Assert
        Truth.assertThat(gameSeriesActualResult).isEqualTo(expectedResult)
        Truth.assertThat(amiiboByGameSeriesActualResult).isEqualTo(expectedResult)
        Truth.assertThat(infoAboutAmiiboActualResult).isEqualTo(expectedResult)
    }

    @Test
    fun testGetGameSeriesFromStorage() {
        //Arrange
        every { sharedPreferences.getString(GAME_SERIES_PREFERENCES_KEY, null) } returns GAME_SERIES_FROM_STORAGE
        val expectedResult = GAME_SERIES_RESPONSE

        //Act
        val actualResult = amiiboStorage.getGameSeriesFromStorage(GAME_SERIES_PREFERENCES_KEY)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun testGetAmiiboByGameSeries() {
        //Arrange
        every { sharedPreferences.getString(GAME_SERIES_KEY, null) } returns AMIIBO_LIST_FROM_STORAGE
        val expectedResult = AMIIBO_LIST_EXPECTED_RESPONSE

        //Act
        val actualResult = amiiboStorage.getAmiiboByGameSeriesFromStorage(GAME_SERIES_KEY)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun testGetInfoAboutAmiibo() {
        //Arrange
        every { sharedPreferences.getString(AMIIBO_TAIL, null) } returns ABOUT_AMIIBO_FROM_STORAGE
        val expectedResult = AMIIBO_INFO_EXPECTED_RESPONSE

        //Act
        val actualResult = amiiboStorage.getInfoAboutAmiiboFromStorage(AMIIBO_TAIL)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    companion object {
        private const val GAME_SERIES_KEY = "0x000"
        private const val AMIIBO_TAIL = "000e0002"
        private const val GAME_SERIES_PREFERENCES_KEY = "game_series"

        private const val GAME_SERIES_FROM_STORAGE = """{
	    "amiibo": [
		        {
			    "key": "0x000",
			    "name": "Super Mario"
		        }
            ]
        }"""

        private val GAME_SERIES_RESPONSE = Amiibo(listOf(GameSeriesModel(key = "0x000", "Super Mario")))

        private const val AMIIBO_LIST_FROM_STORAGE = """{
	    "amiibo": [
		        {
			        "image": "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00000002.png",
			        "name": "Mario",
			        "tail": "00000002"
		        }
            ]
        }"""

        private val AMIIBO_LIST_EXPECTED_RESPONSE = Amiibo(listOf(
            AmiiboModelMinimal(
                tail = "00000002",
                image = "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00000002.png",
                name = "Mario")
        ))

        private const val ABOUT_AMIIBO_FROM_STORAGE = """{
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

        private val AMIIBO_INFO_EXPECTED_RESPONSE = Amiibo(listOf(
            AmiiboModel(
                amiiboSeries = "Super Smash Bros.",
                gameSeries = "The Legend of Zelda",
                image = "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01010000-000e0002.png",
                name = "Zelda",
                type = "Figure")
        ))
    }
}