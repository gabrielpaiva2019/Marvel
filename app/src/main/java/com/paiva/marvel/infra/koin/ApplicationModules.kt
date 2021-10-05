package com.paiva.marvel.infra.koin

import com.paiva.marvel.BuildConfig
import com.paiva.marvel.api.MarvelApi
import com.paiva.marvel.screens.heroesList.HeroesViewModel
import com.paiva.marvel.service.MarvelService
import okhttp3.OkHttpClient
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

val connectionModule = module {

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.GATEWAY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { provideHttpClient() }
    single { provideRetrofit() }

}
