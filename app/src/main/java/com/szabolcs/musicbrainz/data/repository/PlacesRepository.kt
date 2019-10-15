package com.szabolcs.musicbrainz.data.repository

import com.szabolcs.musicbrainz.data.model.remote.PlaceResponse

interface PlacesRepository {

    suspend fun searchPlaces(query: String, limit: Int): List<PlaceResponse>

    fun cancelAllRequests()
}