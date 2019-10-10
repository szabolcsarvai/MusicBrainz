package com.szabolcs.musicbrainz.feature

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.szabolcs.musicbrainz.data.SearchRecordingInteractor

class MainViewModel(private val searchRecordingInteractor: SearchRecordingInteractor) : ViewModel() {

    val fetchedSize = ObservableField<String>()

    fun search() = searchRecordingInteractor.searchRecordings("parngo graszt")

    override fun onCleared() {
        super.onCleared()
        searchRecordingInteractor.cancelAllRequests()
    }
}