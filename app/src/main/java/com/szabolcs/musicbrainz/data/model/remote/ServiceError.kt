package com.szabolcs.musicbrainz.data.model.remote

import java.io.IOException

data class ServiceError(override val message: String, val errorCode: Int = 0) : IOException()