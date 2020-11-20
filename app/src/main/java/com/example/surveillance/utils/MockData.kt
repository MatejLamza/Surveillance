package com.example.surveillance.utils

import com.example.surveillance.data.LicensePlate

object MockData {
    val cityCodes = arrayListOf<String>(
        "BJ",
        "BM",
        "ČK",
        "DA",
        "DE",
        "DJ",
        "DU",
        "GS",
        "IM",
        "KA",
        "KC",
        "KR",
        "KT",
        "KŽ",
        "MA",
        "NA",
        "MG",
        "OG",
        "OS",
        "PU",
        "PŽ",
        "RI",
        "SB",
        "SK",
        "SL",
        "ST",
        "ŠI",
        "VK",
        "VT",
        "VU",
        "VŽ",
        "ZD",
        "ZG",
        "ŽU"
    )
    val licensePlates = mutableSetOf(
        LicensePlate("ZG-1523"),
        LicensePlate("ZG-7613-AA"),
        LicensePlate("ZG-1711-KB"),
        LicensePlate("ZG-9396-NO")
    )
}