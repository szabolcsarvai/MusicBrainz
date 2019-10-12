package com.szabolcs.musicbrainz.data.model.remote

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("coordinates") val coordinates: Coordinates?,
    @SerializedName("life-span") val lifeSpan: LifeSpan?
)