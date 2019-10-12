package com.szabolcs.musicbrainz.data

import androidx.lifecycle.Transformations
import com.google.android.gms.maps.model.LatLng
import com.szabolcs.musicbrainz.data.model.Place
import com.szabolcs.musicbrainz.data.model.remote.PlaceResponse
import com.szabolcs.musicbrainz.data.repository.PlacesRepositoryImpl

class SearchPlacesInteractor(private val recordingRepository: PlacesRepositoryImpl) : SearchPlacesUseCase {

    override fun searchPlaces(query: String, limit: Int) = Transformations.map(recordingRepository.searchPlaces(query, limit)) { newData ->
        newData.filter {
            it.coordinates != null && it.lifeSpan != null
        }.map { placeResponse ->
            placesMapper(placeResponse)
        }
    }

    override fun cancelAllRequests() = recordingRepository.cancelAllRequests()

    private val placesMapper: (PlaceResponse) -> Place = { placeResponse ->
        Place(
            id = placeResponse.id,
            name = placeResponse.name,
            latLng = LatLng(placeResponse.coordinates!!.latitude , placeResponse.coordinates.longitude),
            lifeSpan = placeResponse.lifeSpan!!
        )
    }
}