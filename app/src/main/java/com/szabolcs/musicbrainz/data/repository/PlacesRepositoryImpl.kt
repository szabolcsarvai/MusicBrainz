package com.szabolcs.musicbrainz.data.repository

import com.szabolcs.musicbrainz.ControlledRunner
import com.szabolcs.musicbrainz.data.api.NetworkingManager
import com.szabolcs.musicbrainz.data.model.remote.PlaceResponse
import com.szabolcs.musicbrainz.data.model.remote.SearchResponse
import com.szabolcs.musicbrainz.data.model.remote.hasValidCoordinates
import com.szabolcs.musicbrainz.data.model.remote.hasValidLifeSpan
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PlacesRepositoryImpl(private val networkingManager: NetworkingManager) : PlacesRepository {

    private val parentJob = Job()

    var controlledRunner = ControlledRunner<List<PlaceResponse>>()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO

    override suspend fun searchPlaces(query: String, limit: Int): List<PlaceResponse> {
        return controlledRunner.cancelPreviousThenRun {
            var isNotDone = true
            var offset = 0
            val list = mutableListOf<PlaceResponse>()
            var retrievedData: SearchResponse?

            while (isNotDone) {
                retrievedData = networkingManager.placesService.searchPlaces(query, limit, offset)
                list.addAll(retrievedData.placesResponses)
                if (retrievedData.count <= retrievedData.offset + limit) {
                    isNotDone = false
                }
                offset = retrievedData.offset + limit
            }

            list.filter { placeResponse ->
                placeResponse.hasValidCoordinates() && placeResponse.hasValidLifeSpan()
            }
        }
    }

    override fun cancelAllRequests() = coroutineContext.cancel()
}