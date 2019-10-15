package com.szabolcs.musicbrainz.data.model.remote

import com.google.gson.annotations.SerializedName

data class LifeSpan(
    @SerializedName("begin") val begin: String?,
    @SerializedName("ended") val ended: Boolean?
)

fun LifeSpan.getLifeSpanInSeconds(): Int {
    begin?.let {
        return begin.substring(0, 4).toInt() - 1990
    }
    return 0
}