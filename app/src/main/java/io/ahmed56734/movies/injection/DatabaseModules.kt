package io.ahmed56734.movies.injection

import androidx.room.Room
import io.ahmed56734.movies.data.local.LocalDataSource
import io.ahmed56734.movies.data.local.MoviesDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val databaseModule = module {

    single {
        Room.databaseBuilder(androidContext(), MoviesDatabase::class.java, "moviesDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<MoviesDatabase>().movieDao()
    }

    single {
        LocalDataSource(get())
    }
}