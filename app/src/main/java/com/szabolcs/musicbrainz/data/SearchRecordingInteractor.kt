package com.szabolcs.musicbrainz.data

import com.szabolcs.musicbrainz.data.repository.RecordingRepositoryImpl

class SearchRecordingInteractor(private val recordingRepository: RecordingRepositoryImpl) : SearchRecordingUseCase {

    override fun searchRecordings(query: String, limit: Int) = recordingRepository.searchRecordings(query, limit)

    override fun cancelAllRequests() = recordingRepository.cancelAllRequests()
}