package com.example.surveillance.di.modules

import com.example.surveillance.BuildConfig
import com.example.surveillance.data.remote.LicensePlateAPI
import com.example.surveillance.di.Qualifiers
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single<GsonConverterFactory> {
        GsonConverterFactory.create(get<Gson>())
    }

    single {
        OkHttpClient()
            .newBuilder()
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(get<String>(Qualifiers.apiFullURL))
            .addConverterFactory(get<GsonConverterFactory>())
            .client(get())
            .build()
    }

    single(Qualifiers.apiFullURL) { BuildConfig.API_URL }
    single { get<Retrofit>().create(LicensePlateAPI::class.java) }

}