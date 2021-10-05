package com.paiva.marvel.infra.koin

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.paiva.marvel.BuildConfig
import com.paiva.marvel.api.MarvelApi
import com.paiva.marvel.screens.heroesList.HeroesViewModel
import com.paiva.marvel.service.MarvelService
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val heroesModules = module {
    viewModel { HeroesViewModel(get()) }
}

val serviceModules = module {
    single { MarvelService(get()) }
}

val apiModule = module {
    fun provideUserApi(retrofit: Retrofit): MarvelApi {
        return retrofit.create(MarvelApi::class.java)
    }

    single { provideUserApi(get()) }
}

val netModule = module {


    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        return okHttpClientBuilder.build()
    }



    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }


    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.GATEWAY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    single { provideHttpClient() }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }

}
