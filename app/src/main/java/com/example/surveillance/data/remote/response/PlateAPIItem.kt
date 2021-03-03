package com.example.surveillance.data.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlateAPIItem(
    val detected: Boolean,
    val plate: String,
    val time: String
) : Parcelable