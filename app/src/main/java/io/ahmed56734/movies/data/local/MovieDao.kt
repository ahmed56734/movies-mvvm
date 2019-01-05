package io.ahmed56734.movies.data.local

import androidx.room.Insert
import androidx.room.Query
import io.ahmed56734.movies.data.models.Movie

interface MovieDao {

    @Query("select * from movies order by popularity desc")
    fun getAllPopular(): List<Movie>

    @Insert
    fun insertAll(movies: List<Movie>): IntArray
}