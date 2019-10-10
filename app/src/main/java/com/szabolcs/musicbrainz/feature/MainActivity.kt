package com.szabolcs.musicbrainz.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.szabolcs.musicbrainz.MainBinding
import com.szabolcs.musicbrainz.R
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<MainBinding>(this, R.layout.activity_main).also {
            it.viewModel = viewModel
        }
        binding.button.setOnClickListener {
            viewModel.search().observe(this, Observer { response ->
                viewModel.fetchedSize.set(response.size.toString())
            })
        }
    }
}
