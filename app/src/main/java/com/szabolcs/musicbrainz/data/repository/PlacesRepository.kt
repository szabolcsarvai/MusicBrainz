package com.szabolcs.musicbrainz.data.repository

import com.szabolcs.musicbrainz.data.model.remote.PlaceResponse
import com.szabolcs.musicbrainz.data.model.remote.Response

interface PlacesRepository {

    suspend fun searchPlaces(query: String, limit: Int): Response<PlaceResponse>
}