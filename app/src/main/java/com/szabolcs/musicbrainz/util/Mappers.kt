package com.szabolcs.musicbrainz.util

import com.google.android.gms.maps.model.LatLng
import com.szabolcs.musicbrainz.data.model.Place
import com.szabolcs.musicbrainz.data.model.remote.PlaceResponse
import com.szabolcs.musicbrainz.data.model.remote.getLifeSpanInSeconds

val placesMapper: (PlaceResponse) -> Place = { placeResponse ->
    Place(
        id = placeResponse.id,
        name = placeResponse.name,
        latLng = LatLng(placeResponse.coordinates!!.latitude, placeResponse.coordinates.longitude),
        lifeSpan = placeResponse.lifeSpan.getLifeSpanInSeconds()
    )
}