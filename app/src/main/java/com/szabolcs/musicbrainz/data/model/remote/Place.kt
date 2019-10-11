package com.szabolcs.musicbrainz.data.model.remote

import com.google.gson.annotations.SerializedName

data class Place(@SerializedName("title") val title: String)