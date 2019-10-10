package com.szabolcs.musicbrainz

import android.app.Application
import com.szabolcs.musicbrainz.data.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MusicBrainzApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MusicBrainzApplication)
            modules(
                listOf(
                    featureModule,
                    repositoryModule,
                    networkingModule,
                    interactorModule,
                    wrapperModule
                )
            )
        }
    }
}