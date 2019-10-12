package com.szabolcs.musicbrainz.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.szabolcs.musicbrainz.data.api.NetworkingManager
import com.szabolcs.musicbrainz.data.model.remote.PlaceResponse
import com.szabolcs.musicbrainz.data.model.remote.SearchResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PlacesRepositoryImpl(private val networkingManager: NetworkingManager) : PlacesRepository {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO

    override fun searchPlaces(query: String, limit: Int): LiveData<MutableList<PlaceResponse>> {
        return liveData(coroutineContext) {
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
            list.filter {
                it.coordinates != null && it.lifeSpan != null
            }
            emit(list)
        }
    }

    override fun cancelAllRequests() = coroutineContext.cancel()
}