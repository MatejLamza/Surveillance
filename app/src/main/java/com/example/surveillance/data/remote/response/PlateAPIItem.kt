package com.example.surveillance.data.remote.response

data class PlateAPIItem(
    val detected: Boolean,
    val plate: String,
    val time: String
)