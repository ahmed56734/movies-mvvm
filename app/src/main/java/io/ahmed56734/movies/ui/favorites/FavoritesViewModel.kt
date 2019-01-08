package io.ahmed56734.movies.ui.favorites

import androidx.lifecycle.ViewModel
import io.ahmed56734.movies.data.models.Movie
import io.ahmed56734.movies.data.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

class FavoritesViewModel(private val moviesRepository: MoviesRepository) : ViewModel(), CoroutineScope {
    private val TAG = FavoritesViewModel::class.java.simpleName

    private val compositeJob = Job()
    override val coroutineContext: CoroutineContext
        get() = compositeJob + Dispatchers.Main

    val favoriteMoviesPagedList = moviesRepository.getfavoriteMoviesPagedList()


    fun toggleFavorites(movie: Movie) = runBlocking {
        moviesRepository.toggleFavorites(movie)
    }


    override fun onCleared() {
        super.onCleared()
        compositeJob.cancel()
    }
}
