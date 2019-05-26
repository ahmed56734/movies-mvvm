package io.ahmed56734.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.ahmed56734.movies.data.models.Movie
import io.ahmed56734.movies.data.models.SearchQuery

@Database(entities = [Movie::class, SearchQuery::class], version = 3)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun searchQueryDao(): SearchQueryDao
}