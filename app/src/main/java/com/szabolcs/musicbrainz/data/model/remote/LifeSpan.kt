package com.szabolcs.musicbrainz.data.model.remote

import com.google.gson.annotations.SerializedName

data class LifeSpan(
    @SerializedName("begin") val begin: String?,
    @SerializedName("ended") val ended: String?
)