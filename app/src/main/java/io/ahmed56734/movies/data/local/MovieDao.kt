package io.ahmed56734.movies.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import io.ahmed56734.movies.data.models.Movie

@Dao
interface MovieDao {

    @Query("select * from movies order by popularity desc")
    fun getAllPopular(): List<Movie>

    @Query("select * from movies order by popularity desc")
    fun getAllPopularDataFactory(): DataSource.Factory<Int, Movie>

    @Query("update movies set isFavorite = 1 where id = :movieId")
    fun addToFavorites(movieId: Long): Int

    @Query("select * from movies where isFavorite = 1 order by popularity desc")
    fun getAllFavorites(): DataSource.Factory<Int, Movie>

    @Query("update movies set isFavorite = 0 where id = :movieId")
    fun removeFromFavorites(movieId: Long): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(movies: List<Movie>)
}