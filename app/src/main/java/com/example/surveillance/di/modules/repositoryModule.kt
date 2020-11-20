package com.example.surveillance.di.modules

import com.example.surveillance.data.repository.LicensePlateRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { LicensePlateRepository(licensePlateAPI = get()) }
}