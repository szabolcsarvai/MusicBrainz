package com.szabolcs.musicbrainz.data.model.remote

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("created") val timestamp: String,
    @SerializedName("count") val count: Int,
    @SerializedName("offset") val offset: Int,
    @SerializedName("recordings") val recordings: List<Recording>
)