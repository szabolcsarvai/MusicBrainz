package com.szabolcs.musicbrainz.feature

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.szabolcs.musicbrainz.data.SearchRecordingInteractor

class MainViewModel(private val searchRecordingInteractor: SearchRecordingInteractor) : ViewModel() {

    val fetchedSize = ObservableField<String>()
    private val query = MutableLiveData<String>()

    val records = Transformations.switchMap(query) { query ->
        searchRecordingInteractor.searchRecordings(query)
    }

    fun search(query: String) {
        this.query.postValue(query)
    }

    override fun onCleared() {
        super.onCleared()
        searchRecordingInteractor.cancelAllRequests()
    }
}