package com.szabolcs.musicbrainz.data.api

import com.google.gson.Gson
import com.szabolcs.musicbrainz.util.ResourceWrapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkingManager(gson: Gson, resourceWrapper: ResourceWrapper) {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(ErrorInterceptor(resourceWrapper, gson))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://musicbrainz.org/ws/2/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val placesService: MusicService = retrofit.create(MusicService::class.java)
}