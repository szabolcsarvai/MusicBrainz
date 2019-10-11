package com.szabolcs.musicbrainz.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.szabolcs.musicbrainz.MainBinding
import com.szabolcs.musicbrainz.R
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<MainBinding>(this, R.layout.activity_main).also {
            it.viewModel = viewModel
        }

        viewModel.records.observe(this, Observer {
            viewModel.loading.set(false)
            viewModel.fetchedSize.set(it.size.toString())
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu).also {
            val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
            searchView.setOnQueryTextListener(this)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?) = true

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.search(it) }
        return true
    }
}
