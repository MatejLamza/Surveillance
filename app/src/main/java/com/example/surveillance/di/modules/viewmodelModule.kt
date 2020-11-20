package com.example.surveillance.di.modules

import com.example.surveillance.flow.licensePlate.LicensePlateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule = module {

    viewModel { LicensePlateViewModel(get()) }
}