package com.szabolcs.musicbrainz.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.szabolcs.musicbrainz.data.api.NetworkingManager
import com.szabolcs.musicbrainz.data.model.remote.Recording
import com.szabolcs.musicbrainz.data.model.remote.SearchResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RecordingRepositoryImpl(private val networkingManager: NetworkingManager) : RecordingRepository {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO

    override fun searchRecordings(query: String, limit: Int): LiveData<MutableList<Recording>> {
        return liveData(coroutineContext) {
            var isNotDone = true
            var offset = 0
            val list = mutableListOf<Recording>()
            var retrievedData: SearchResponse?

            while (isNotDone) {
                retrievedData = networkingManager.musicService.searchRecordings(query, limit, offset)
                list.addAll(retrievedData.recordings)
                Log.d("TAG", "fetched data offset here: ${retrievedData.offset}")
                if (retrievedData.count <= retrievedData.offset + limit) {
                    isNotDone = false
                }
                offset = retrievedData.offset + limit
            }
            Log.d("TAG", "fetched data here: ${list.size}")
            emit(list)
        }
    }

    override fun cancelAllRequests() = coroutineContext.cancel()
}