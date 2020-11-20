package com.example.surveillance

import android.app.Application
import com.example.surveillance.di.SurveillanceAppDI

class SurveillanceApplication : Application() {
    private val surveillanceAppDI: SurveillanceAppDI by lazy { SurveillanceAppDI(this) }
    override fun onCreate() {
        super.onCreate()
        surveillanceAppDI.initialize()
    }
}
