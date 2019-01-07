package io.ahmed56734.movies.data.local

import io.ahmed56734.movies.data.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource(private val movieDao: MovieDao) {


    suspend fun saveMovies(movies: List<Movie>) =
        movieDao.insertAll(movies)


    fun getPopularMoviesDataFactory() = movieDao.getAllPopularDataFactory()

    suspend fun toggleFavorites(movie: Movie): Int = withContext(Dispatchers.IO) {
        if (movie.isFavorite) {
            movieDao.removeFromFavorites(movieId = movie.id)
        } else {
            movieDao.addToFavorites(movieId = movie.id)
        }
    }

    fun getFavoriteMovies() = movieDao.getAllFavorites()

}