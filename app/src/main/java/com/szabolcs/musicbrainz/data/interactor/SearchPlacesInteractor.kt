package com.szabolcs.musicbrainz.data.interactor

import androidx.lifecycle.Transformations
import com.szabolcs.musicbrainz.data.repository.PlacesRepositoryImpl
import com.szabolcs.musicbrainz.util.placesMapper

class SearchPlacesInteractor(private val recordingRepository: PlacesRepositoryImpl) : SearchPlacesUseCase {

    override fun searchPlaces(query: String, limit: Int) = Transformations.map(recordingRepository.searchPlaces(query, limit)) { newData ->
        newData.map { placeResponse ->
            placesMapper(placeResponse)
        }
    }

    override fun cancelAllRequests() = recordingRepository.cancelAllRequests()
}