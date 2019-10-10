package com.szabolcs.musicbrainz.data.di

import com.google.gson.GsonBuilder
import com.szabolcs.musicbrainz.data.SearchRecordingInteractor
import com.szabolcs.musicbrainz.data.api.NetworkingManager
import com.szabolcs.musicbrainz.data.repository.RecordingRepositoryImpl
import com.szabolcs.musicbrainz.feature.MainViewModel
import com.szabolcs.musicbrainz.util.ResourceWrapper
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    viewModel { MainViewModel(get()) }
}

val interactorModule = module {
    single { SearchRecordingInteractor(get()) }
}

val repositoryModule = module {
    single { RecordingRepositoryImpl(get()) }
}

val networkingModule = module {
    single { GsonBuilder().create() }
    factory { NetworkingManager(get(), get()) }
}

val wrapperModule = module {
    single { ResourceWrapper(androidContext()) }
}