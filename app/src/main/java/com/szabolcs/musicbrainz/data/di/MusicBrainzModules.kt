package com.szabolcs.musicbrainz.data.di

import com.google.gson.GsonBuilder
import com.szabolcs.musicbrainz.data.interactor.SearchPlacesInteractor
import com.szabolcs.musicbrainz.data.api.NetworkingManager
import com.szabolcs.musicbrainz.data.repository.PlacesRepositoryImpl
import com.szabolcs.musicbrainz.feature.MainViewModel
import com.szabolcs.musicbrainz.util.ResourceWrapper
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    viewModel { MainViewModel(get()) }
}

val interactorModule = module {
    single { SearchPlacesInteractor(get()) }
}

val repositoryModule = module {
    single { PlacesRepositoryImpl(get()) }
}

val networkingModule = module {
    single { GsonBuilder().create() }
    factory { NetworkingManager(get(), get()) }
}

val wrapperModule = module {
    single { ResourceWrapper(androidContext()) }
}