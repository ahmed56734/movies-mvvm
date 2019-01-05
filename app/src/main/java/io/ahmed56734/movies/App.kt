package io.ahmed56734.movies

import android.app.Application
import io.ahmed56734.movies.injection.databaseModule
import io.ahmed56734.movies.injection.networkingModule
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(networkingModule, databaseModule))
    }
}