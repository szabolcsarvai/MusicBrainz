package com.szabolcs.musicbrainz.data.model

import com.google.android.gms.maps.model.LatLng
import com.szabolcs.musicbrainz.data.model.remote.LifeSpan

data class Place(
    val id: String,
    val name: String,
    val latLng: LatLng,
    val lifeSpan: LifeSpan
)