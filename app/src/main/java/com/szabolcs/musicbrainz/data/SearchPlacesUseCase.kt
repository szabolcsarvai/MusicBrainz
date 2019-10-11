package com.szabolcs.musicbrainz.data

import androidx.lifecycle.LiveData
import com.szabolcs.musicbrainz.data.model.remote.Place

interface SearchPlacesUseCase {

    fun searchPlaces(query: String, limit: Int = 100): LiveData<MutableList<Place>>

    fun cancelAllRequests()
}