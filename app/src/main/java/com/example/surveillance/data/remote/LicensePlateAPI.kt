package com.example.surveillance.data.remote

import com.example.surveillance.data.Plate
import com.example.surveillance.data.remote.response.PlateAPI
import retrofit2.http.GET
import retrofit2.http.Path

interface LicensePlateAPI {

    @GET("check-licence-plate/{plate}/2020-12-01T18:45")
    suspend fun fetchLicesePlate(@Path("plate") plate: String): Plate

    @GET("check-licence-plate/{plate}/2020-12-01T18:45")
    suspend fun fetchPlates(@Path("plate") plate: String): PlateAPI

}