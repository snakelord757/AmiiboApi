package com.example.amiiboapi.data.datasource.storage

import android.content.SharedPreferences
import com.example.amiiboapi.data.mapper.ResponseMapper
import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
import com.example.amiiboapi.data.model.AmiiboModelMinimal
import com.example.amiiboapi.data.model.GameSeriesModel

class AmiiboStorageImpl(
    private val settingPreferences: SharedPreferences,
    private val amiiboSharedPreferences: SharedPreferences
) : AmiiboStorage {

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
        val preferencesEditor = amiiboSharedPreferences.edit()
        preferencesEditor.putString(key, value)
        preferencesEditor.apply()
    }

    override fun getForceStoreParameter(parameterKey: String): Boolean {
        return settingPreferences.getBoolean(parameterKey, false)
    }

    private inline fun <reified T> getDataFromSharedPreferences(key: String): Amiibo<T>? {
        val dataJson: String? = amiiboSharedPreferences.getString(key, null)
        if (dataJson != null) {
            return ResponseMapper.mapResponse(dataJson)
        }
        return null
    }
}