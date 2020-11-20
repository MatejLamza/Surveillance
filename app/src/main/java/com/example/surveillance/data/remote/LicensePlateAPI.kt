package com.example.surveillance.data.remote

import com.example.surveillance.data.CityCode
import retrofit2.http.GET

interface LicensePlateAPI {

    @GET("test/item")
    suspend fun fetchLicesePlate()

    @GET("city-codes")
    suspend fun fetchCityCodes(): List<CityCode>
}