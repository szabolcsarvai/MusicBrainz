package com.szabolcs.musicbrainz.data

import androidx.lifecycle.Transformations
import com.szabolcs.musicbrainz.data.model.remote.hasValidCoordinates
import com.szabolcs.musicbrainz.data.model.remote.hasValidLifeSpan
import com.szabolcs.musicbrainz.data.repository.PlacesRepositoryImpl
import com.szabolcs.musicbrainz.util.placesMapper

class SearchPlacesInteractor(private val recordingRepository: PlacesRepositoryImpl) : SearchPlacesUseCase {

    override fun searchPlaces(query: String, limit: Int) = Transformations.map(recordingRepository.searchPlaces(query, limit)) { newData ->
        newData.filter { placeResponse ->
            placeResponse.hasValidCoordinates() && placeResponse.hasValidLifeSpan()
        }.map { placeResponse ->
            placesMapper(placeResponse)
        }
    }

    override fun cancelAllRequests() = recordingRepository.cancelAllRequests()
}