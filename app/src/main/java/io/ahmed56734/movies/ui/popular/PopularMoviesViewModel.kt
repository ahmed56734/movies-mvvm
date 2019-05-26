package io.ahmed56734.movies.ui.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.ahmed56734.movies.data.models.Movie
import io.ahmed56734.movies.data.repository.MoviesRepository
import io.ahmed56734.movies.util.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PopularMoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel(), CoroutineScope {

    private val TAG = PopularMoviesViewModel::class.java.simpleName

    private val compositeJob = Job()
    override val coroutineContext: CoroutineContext
        get() = compositeJob + Dispatchers.Main

    private val repoResult = moviesRepository.popularMovies()

    val pagedList = repoResult.pagedList
    val refreshState = repoResult.refreshState
    val loadMoreState = repoResult.networkState

    val snackBarEvents: MutableLiveData<Event<String>> = MutableLiveData()

    fun retry() {
        repoResult.retry.invoke()
    }

    fun refresh() {
        repoResult.refresh.invoke()
    }

    fun toggleFavorites(movie: Movie) = launch {
        val oldValue = movie.isFavorite
        moviesRepository.toggleFavorites(movie)
        val snackBarMessage =
            if (oldValue)
                "${movie.title} removed from favorites"
            else
                "${movie.title} added to favorites"

        snackBarEvents.value = Event(snackBarMessage)
    }

    override fun onCleared() {
        super.onCleared()
        compositeJob.cancel()
    }

}
