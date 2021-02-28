package com.example.surveillance.data.repository

import com.example.surveillance.data.CityCode
import com.example.surveillance.data.Plate
import com.example.surveillance.data.remote.LicensePlateAPI
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class LicensePlateRepository(private val licensePlateAPI: LicensePlateAPI) {

    private val gson: Gson by lazy {
        Gson()
    }

    //TODO add fetching license plates
    suspend fun getLicensePlate(plate: String): Plate {
        return withContext(IO) {
            licensePlateAPI.fetchLicesePlate(plate)
        }
    }

    suspend fun getAllCityCodes(): List<CityCode> {
        return withContext(IO) {
            licensePlateAPI.fetchCityCodes()
        }
    }
}