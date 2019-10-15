package com.szabolcs.musicbrainz.data.interactor

import androidx.lifecycle.LiveData
import com.szabolcs.musicbrainz.data.model.Place

interface SearchPlacesUseCase {

    fun searchPlaces(query: String, limit: Int = 100): LiveData<List<Place>>

    fun cancelAllRequests()
}