package com.szabolcs.musicbrainz.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.szabolcs.musicbrainz.MainBinding
import com.szabolcs.musicbrainz.R
import com.szabolcs.musicbrainz.data.model.Place
import com.szabolcs.musicbrainz.data.model.remote.PlaceResponse
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, OnMapReadyCallback {

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<MainBinding>(this, R.layout.activity_main).also {
            it.viewModel = viewModel
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.records.observe(this, Observer {
            viewModel.loading.set(false)
            viewModel.fetchedSize.set(it.size.toString())
            it.forEach { place ->
                addMarker(place)
            }
        })
    }

    private fun addMarker(place: Place) {
        mMap.addMarker(
            MarkerOptions()
                .position(place.latLng)
                .title(place.name)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu).also {
            val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
            searchView.setOnQueryTextListener(this)
        }
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    override fun onQueryTextChange(newText: String?) = true

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.search(it) }
        return true
    }
}
