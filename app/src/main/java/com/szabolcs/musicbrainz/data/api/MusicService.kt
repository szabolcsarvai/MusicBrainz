package com.szabolcs.musicbrainz.data.api

import com.szabolcs.musicbrainz.data.model.remote.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicService {

    @GET("place")
    suspend fun searchPlaces(
        @Query("query") searchTerm: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): SearchResponse
}