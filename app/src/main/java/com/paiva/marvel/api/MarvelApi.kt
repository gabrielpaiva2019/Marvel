package com.paiva.marvel.api

import com.paiva.marvel.model.Heroes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApi {
    @GET("/characters{ts}{apikey}{hash}")
    suspend fun getHeroes(
        @Path("ts") ts: String,
        @Path("apikey") apikey: String,
        @Path("hash") hash: String
    ): Response<Heroes>
}