package com.szabolcs.musicbrainz.data.model.remote

import com.google.gson.annotations.SerializedName

data class Coordinates(
    @SerializedName("latitude") val latitude: Float,
    @SerializedName("longitude") val longitude: Float
)