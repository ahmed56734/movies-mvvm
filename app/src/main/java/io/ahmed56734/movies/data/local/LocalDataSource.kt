package io.ahmed56734.movies.data.local

import io.ahmed56734.movies.data.models.Movie

class LocalDataSource(private val movieDao: MovieDao) {


    suspend fun saveMovies(movies: List<Movie>) =
        movieDao.insertAll(movies)


    fun getPopularMoviesDataFactory() = movieDao.getAllPopularDataFactory()
}