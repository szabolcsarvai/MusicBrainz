package com.szabolcs.musicbrainz

import android.app.Application
import com.szabolcs.musicbrainz.data.di.networkingModule
import com.szabolcs.musicbrainz.data.di.wrapperModule
import org.koin.core.context.startKoin

class MusicBrainzApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin { listOf(networkingModule, wrapperModule) }
    }
}