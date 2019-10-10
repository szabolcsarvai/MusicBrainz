package com.szabolcs.musicbrainz.data.repository

import androidx.lifecycle.LiveData
import com.szabolcs.musicbrainz.data.model.remote.Recording

interface RecordingRepository {

    fun searchRecordings(query: String, limit: Int): LiveData<MutableList<Recording>>

    fun cancelAllRequests()
}