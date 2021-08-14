package com.example.amiiboapi.data.datasource.storage

import android.content.SharedPreferences
import com.example.amiiboapi.data.mapper.ResponseMapper
import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel

class AmiiboStorageImpl(private val sharedPreferences: SharedPreferences) : AmiiboStorage {

    override fun getGameSeriesFromStorage(key: String): Amiibo<GameSeriesModel>? {
        return getDataFromSharedPreferences(key)
    }

    override fun getAmiiboByGameSeriesFromStorage(gameSeriesKey: String): Amiibo<AmiiboModelMinimal>? {
        return getDataFromSharedPreferences(gameSeriesKey)
    }

    override fun getInfoAboutAmiiboFromStorage(amiiboTailKey: String): Amiibo<AmiiboModel>? {
        return getDataFromSharedPreferences(amiiboTailKey)
    }

    override fun insertIntoStorage(key: String, value: String) {
        val preferencesEditor = sharedPreferences.edit()
        preferencesEditor.putString(key, value)
        preferencesEditor.apply()
    }
    
    private inline fun <reified T> getDataFromSharedPreferences(key: String): Amiibo<T>? {
        val dataJson: String? = sharedPreferences.getString(key, null)
        if (dataJson != null) {
            return ResponseMapper.mapResponse(dataJson)
        }
        return null
    }
}