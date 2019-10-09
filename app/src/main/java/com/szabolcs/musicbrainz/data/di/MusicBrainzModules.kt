package com.szabolcs.musicbrainz.data.di

import com.google.gson.GsonBuilder
import com.szabolcs.musicbrainz.data.api.NetworkingManager
import com.szabolcs.musicbrainz.util.ResourceWrapper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val networkingModule = module {
    single { GsonBuilder().create() }
    single { NetworkingManager(get(), get()) }
}

val wrapperModule = module {
    single { ResourceWrapper(androidContext()) }
}