package com.szabolcs.musicbrainz.data.repository

import androidx.lifecycle.LiveData
import com.szabolcs.musicbrainz.data.model.remote.Place

interface PlacesRepository {

    fun searchPlaces(query: String, limit: Int): LiveData<MutableList<Place>>

    fun cancelAllRequests()
}