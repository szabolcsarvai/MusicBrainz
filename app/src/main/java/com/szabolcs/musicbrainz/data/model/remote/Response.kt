package com.szabolcs.musicbrainz.data.model.remote

data class Response<T>(val listOfData: List<T>? = null, val error: ServiceError? = null)