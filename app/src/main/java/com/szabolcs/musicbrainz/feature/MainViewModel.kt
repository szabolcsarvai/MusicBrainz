package com.szabolcs.musicbrainz.feature

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.szabolcs.musicbrainz.data.interactor.SearchPlacesInteractor
import com.szabolcs.musicbrainz.data.model.PlaceMarker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(private val searchRecordingInteractor: SearchPlacesInteractor) : ViewModel() {

    private val query = MutableLiveData<String>()
    val loading = ObservableBoolean(false)
    var markers = mutableListOf<PlaceMarker>()
    val records = Transformations.switchMap(query) { query ->
        searchRecordingInteractor.searchPlaces(query)
    }

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                markers.forEach { placeMarker ->
                    --placeMarker.lifeSpan
                    if (placeMarker.lifeSpan == 0) {
                        placeMarker.marker.remove()
                    }
                }
                markers = markers.filter {
                    it.lifeSpan > 0
                }.toMutableList()
            }
        }
    }

    fun search(query: String) {
        loading.set(true)
        this.query.postValue(query)
    }

    override fun onCleared() {
        super.onCleared()
        searchRecordingInteractor.cancelAllRequests()
    }
}