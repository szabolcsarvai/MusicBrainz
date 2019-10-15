package com.szabolcs.musicbrainz.data.repository

import com.szabolcs.musicbrainz.ControlledRunner
import com.szabolcs.musicbrainz.data.api.NetworkingManager
import com.szabolcs.musicbrainz.data.model.remote.*

class PlacesRepositoryImpl(private val networkingManager: NetworkingManager) : PlacesRepository {

    private var controlledRunner = ControlledRunner<Response<PlaceResponse>>()

    override suspend fun searchPlaces(query: String, limit: Int): Response<PlaceResponse> {
        return controlledRunner.cancelPreviousThenRun {
            var isNotDone = true
            var offset = 0
            val list = mutableListOf<PlaceResponse>()
            var retrievedData: SearchResponse?

            while (isNotDone) {
                try {
                    retrievedData = networkingManager.placesService.searchPlaces(query, limit, offset)

                    list.addAll(retrievedData.placesResponses)
                    if (retrievedData.count <= retrievedData.offset + limit) {
                        isNotDone = false
                    }
                    offset = retrievedData.offset + limit
                } catch (exception: ServiceError) {
                    return@cancelPreviousThenRun Response(error = exception)
                }
            }

            Response(listOfData = list.filter { placeResponse ->
                placeResponse.hasValidCoordinates() && placeResponse.hasValidLifeSpan()
            })
        }
    }
}