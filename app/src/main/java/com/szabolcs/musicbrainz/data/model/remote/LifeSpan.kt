package com.szabolcs.musicbrainz.data.model.remote

import com.google.gson.annotations.SerializedName

data class LifeSpan(
    @SerializedName("begin") val begin: String?,
    @SerializedName("ended") val ended: Boolean?
)

fun LifeSpan.getOpenYear(): Int {
    begin?.let {
        return begin.substring(0, 3).toInt()
    }
    return 0
}