package io.ahmed56734.movies.data.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.ahmed56734.movies.data.models.Movie

@Dao
interface MovieDao {

    @Query("select * from movies order by popularity desc")
    fun getAllPopular(): List<Movie>

    @Query("select * from movies order by popularity desc")
    fun getAllPopularDataFactory(): DataSource.Factory<Int, Movie>

    @Insert
    fun insertAll(movies: List<Movie>)
}