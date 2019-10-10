package com.szabolcs.musicbrainz.data.model.remote

import com.google.gson.annotations.SerializedName

data class Recording(@SerializedName("title") val title: String)