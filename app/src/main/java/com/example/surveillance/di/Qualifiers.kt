package com.example.surveillance.di

import org.koin.core.qualifier.named

object Qualifiers {
    val apiFullURL by lazy { named("apiFullURL") }
}