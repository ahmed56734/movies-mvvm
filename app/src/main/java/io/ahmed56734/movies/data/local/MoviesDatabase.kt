package io.ahmed56734.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.ahmed56734.movies.data.models.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}