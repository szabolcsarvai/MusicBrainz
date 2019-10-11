package com.szabolcs.musicbrainz.data

import com.szabolcs.musicbrainz.data.repository.PlacesRepositoryImpl

class SearchPlacesInteractor(private val recordingRepository: PlacesRepositoryImpl) : SearchPlacesUseCase {

    override fun searchPlaces(query: String, limit: Int) = recordingRepository.searchPlaces(query, limit)

    override fun cancelAllRequests() = recordingRepository.cancelAllRequests()
}