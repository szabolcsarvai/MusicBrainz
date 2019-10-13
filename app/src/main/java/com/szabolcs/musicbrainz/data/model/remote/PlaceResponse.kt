package com.szabolcs.musicbrainz.data.model.remote

import com.google.gson.annotations.SerializedName
import org.koin.ext.isInt

data class PlaceResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("coordinates") val coordinates: Coordinates?,
    @SerializedName("life-span") val lifeSpan: LifeSpan
)

fun PlaceResponse.hasValidCoordinates(): Boolean =
    coordinates != null

fun PlaceResponse.hasValidLifeSpan(): Boolean {
    lifeSpan.begin?.let { begin ->
        if (!begin.substring(0, 4).isInt()) return false
        if (begin.substring(0, 4).toInt() > 1990) return true
    }
    return false
}