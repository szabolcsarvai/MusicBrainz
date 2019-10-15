package com.szabolcs.musicbrainz.data.interactor

import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.szabolcs.musicbrainz.data.repository.PlacesRepositoryImpl
import com.szabolcs.musicbrainz.util.responseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

class SearchPlacesInteractor(private val recordingRepository: PlacesRepositoryImpl) : SearchPlacesUseCase {

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO

    override fun searchPlaces(query: String, limit: Int) =
        Transformations.map(liveData(coroutineContext) { emit(recordingRepository.searchPlaces(query, limit)) }) { newData ->
            responseMapper(newData)
        }

    override fun cancelAllRequests() = coroutineContext.cancel()
}