package com.szabolcs.musicbrainz.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.szabolcs.musicbrainz.data.api.NetworkingManager
import com.szabolcs.musicbrainz.data.model.remote.Place
import com.szabolcs.musicbrainz.data.model.remote.SearchResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PlacesRepositoryImpl(private val networkingManager: NetworkingManager) : PlacesRepository {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO

    override fun searchPlaces(query: String, limit: Int): LiveData<MutableList<Place>> {
        return liveData(coroutineContext) {
            var isNotDone = true
            var offset = 0
            val list = mutableListOf<Place>()
            var retrievedData: SearchResponse?

            while (isNotDone) {
                retrievedData = networkingManager.placesService.searchPlaces(query, limit, offset)
                list.addAll(retrievedData.places)
                if (retrievedData.count <= retrievedData.offset + limit) {
                    isNotDone = false
                }
                offset = retrievedData.offset + limit
            }
            emit(list)
        }
    }

    override fun cancelAllRequests() = coroutineContext.cancel()
}