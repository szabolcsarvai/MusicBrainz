package com.szabolcs.musicbrainz.data.interactor

import androidx.lifecycle.LiveData
import com.szabolcs.musicbrainz.data.model.Place
import com.szabolcs.musicbrainz.data.model.remote.Response

interface SearchPlacesUseCase {

    fun searchPlaces(query: String, limit: Int = 100): LiveData<Response<Place>>

    fun cancelAllRequests()
}