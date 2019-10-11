package com.szabolcs.musicbrainz.feature

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.szabolcs.musicbrainz.data.SearchPlacesInteractor

class MainViewModel(private val searchRecordingInteractor: SearchPlacesInteractor) : ViewModel() {

    val fetchedSize = ObservableField<String>()
    private val query = MutableLiveData<String>()

    val loading = ObservableBoolean(false)

    val records = Transformations.switchMap(query) { query ->
        searchRecordingInteractor.searchPlaces(query)
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