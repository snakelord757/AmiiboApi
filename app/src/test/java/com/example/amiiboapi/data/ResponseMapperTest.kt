package com.example.amiiboapi.data

import com.example.amiiboapi.data.mapper.ResponseMapper
import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel
import com.google.common.truth.Truth
import com.squareup.moshi.JsonEncodingException
import org.junit.Test
import java.io.EOFException

class ResponseMapperTest {

    @Test
    fun testGameSeriesResponse() {
        //Arrange
        val gameSeriesJson = """{
	    "amiibo": [
		        {
			    "key": "0x000",
			    "name": "Super Mario"
		        }
            ]
        }"""
        val expectedResult = AMIIBO_GAME_SERIES_EXPECTED_RESPONSE
        
        //Act
        val actualResult: Amiibo<GameSeriesModel> = ResponseMapper.mapResponse(gameSeriesJson)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun testAmiiboListResponse() {
        //Arrange
        val amiiboListJson = """{
	    "amiibo": [
		        {
			        "image": "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00000002.png",
			        "name": "Mario",
			        "tail": "00000002"
		        }
            ]
        }"""
        val expectedResult = AMIIBO_LIST_EXPECTED_RESPONSE

        //Act
        val actualResult: Amiibo<AmiiboModelMinimal> = ResponseMapper.mapResponse(amiiboListJson)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun testAmiiboInfoResponse() {
        //Arrange
        val aboutAmiiboJson = """{
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
        val expectedResult = AMIIBO_INFO_EXPECTED_RESPONSE

        //Act
        val actualResult: Amiibo<AmiiboModel> = ResponseMapper.mapResponse(aboutAmiiboJson)

        //Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test(expected = EOFException::class)
    fun testEmptyResponse() {
        //Arrange
        val emptyJson = ""

        //Act
        val actualResult: Amiibo<AmiiboModel> = ResponseMapper.mapResponse(emptyJson)
    }

    @Test(expected = JsonEncodingException::class)
    fun testInvalidResponse() {
        //Arrange
        val invalidJson = """{
	    "amiibo": [
		        {
			        "amiiboSeries": "Super Smash Bros.",
			        "gameSeries": "The Legend of Zelda",
			        "image": "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01010000-000e0002.png",
			        "name": "Zelda",
			        "type": "Figure"
		        }
        }"""

        //Act
        val invalidResponse: Amiibo<AmiiboModel> = ResponseMapper.mapResponse(invalidJson)
    }

    companion object {
        private val AMIIBO_GAME_SERIES_EXPECTED_RESPONSE = Amiibo(
            listOf(GameSeriesModel(key = "0x000", name = "Super Mario")))

        private val AMIIBO_LIST_EXPECTED_RESPONSE = Amiibo(listOf(
            AmiiboModelMinimal(
                tail = "00000002",
                image = "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00000002.png",
                name = "Mario")))

        private val AMIIBO_INFO_EXPECTED_RESPONSE = Amiibo(listOf(
            AmiiboModel(
                amiiboSeries = "Super Smash Bros.",
                gameSeries = "The Legend of Zelda",
                image = "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01010000-000e0002.png",
                name = "Zelda",
                type = "Figure")))
    }
}