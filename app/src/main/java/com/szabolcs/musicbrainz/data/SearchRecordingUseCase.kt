package com.szabolcs.musicbrainz.data

import androidx.lifecycle.LiveData
import com.szabolcs.musicbrainz.data.model.remote.Recording

interface SearchRecordingUseCase {

    fun searchRecordings(query: String, limit: Int = 20): LiveData<MutableList<Recording>>

    fun cancelAllRequests()
}