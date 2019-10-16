package com.szabolcs.musicbrainz.feature

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.szabolcs.musicbrainz.MainBinding
import com.szabolcs.musicbrainz.R
import com.szabolcs.musicbrainz.data.model.Place
import com.szabolcs.musicbrainz.data.model.PlaceMarker
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, OnMapReadyCallback {

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var map: GoogleMap
    private lateinit var binding: MainBinding
    private var query: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<MainBinding>(this, R.layout.activity_main).also {
            it.viewModel = viewModel
        }

        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment)
            .getMapAsync(this)

        viewModel.records.observe(this, Observer { response ->
            viewModel.loading.set(false)
            response.error?.let { error ->
                showSnackbar(error.message)
            }
            response.listOfData?.let { places ->
                addMarkers(places)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu).also {
            val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
            searchView.setOnQueryTextListener(this)
        }
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        query = newText
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            hideKeyboard()
            viewModel.search(it)
        }
        return true
    }

    private fun addMarkers(places: List<Place>) {
        viewModel.markers.clear()
        map.clear()
        places.forEach { place -> addMarker(place) }
    }

    private fun addMarker(place: Place) {
        map.addMarker(
            MarkerOptions()
                .position(place.latLng)
                .title(place.name)
        ).also { marker ->
            viewModel.markers.add(PlaceMarker(marker, place.lifeSpan))
        }
    }

    private fun AppCompatActivity.showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).setAction(R.string.retry) {
            onQueryTextSubmit(query)
        }.show()
    }

    private fun AppCompatActivity.hideKeyboard() {
        currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
