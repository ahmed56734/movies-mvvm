package io.ahmed56734.movies.ui.popular

import androidx.lifecycle.ViewModel;
import io.ahmed56734.movies.data.models.Movie
import io.ahmed56734.movies.data.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

class PopularMoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel(), CoroutineScope {

    private val TAG = PopularMoviesViewModel::class.java.simpleName

    private val compositeJob = Job()
    override val coroutineContext: CoroutineContext
        get() = compositeJob + Dispatchers.IO

    private val repoResult = moviesRepository.popularMovies()

    val pagedList = repoResult.pagedList
    val refreshState = repoResult.refreshState
    val loadMoreState = repoResult.networkState


    fun retry() {
        repoResult.retry.invoke()
    }

    fun refresh() {
        repoResult.refresh.invoke()
    }

    fun toggleFavorites(movie: Movie) = runBlocking {
        moviesRepository.toggleFavorites(movie)
    }

    override fun onCleared() {
        super.onCleared()
        compositeJob.cancel()
    }

}
