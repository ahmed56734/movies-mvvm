package io.ahmed56734.movies.data.local

import androidx.lifecycle.LiveData
import io.ahmed56734.movies.data.models.Movie
import io.ahmed56734.movies.data.models.SearchQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource(private val movieDao: MovieDao, private val searchQueryDao: SearchQueryDao) {


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

    suspend fun getMoviesCount() = movieDao.getCount()

    fun getFavoriteMovies() = movieDao.getAllFavorites()

    suspend fun addSearchQuery(searchQuery: SearchQuery) {
        if (searchQueryDao.getCount() > 50) {
            searchQueryDao.deleteOldest()
        }
        searchQueryDao.insert(searchQuery)
    }

    fun getRecentSearchQueries(): LiveData<List<SearchQuery>> = searchQueryDao.getRecentQueries()

}