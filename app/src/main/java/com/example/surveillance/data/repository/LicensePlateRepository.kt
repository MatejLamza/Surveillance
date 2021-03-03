package com.example.surveillance.data.repository

import com.example.surveillance.data.Plate
import com.example.surveillance.data.remote.LicensePlateAPI
import com.example.surveillance.data.remote.response.PlateAPI
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class LicensePlateRepository(private val licensePlateAPI: LicensePlateAPI) {

    //TODO add fetching license plates
    suspend fun getLicensePlate(plate: String): Plate {
        return withContext(IO) {
            licensePlateAPI.fetchLicesePlate(plate)
        }
    }

    suspend fun fetchPlates(plate: String): PlateAPI {
        return withContext(IO) {
            licensePlateAPI.fetchPlates(plate)
        }
    }

}