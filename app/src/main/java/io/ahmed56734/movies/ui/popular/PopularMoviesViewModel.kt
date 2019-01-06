package io.ahmed56734.movies.ui.popular

import androidx.lifecycle.ViewModel;
import io.ahmed56734.movies.repository.popular.PopularMoviesRepository

class PopularMoviesViewModel(private val popularMoviesRepository: PopularMoviesRepository) : ViewModel() {
    private val TAG = PopularMoviesViewModel::class.java.simpleName

    private val repoResult = popularMoviesRepository.popularMovies()

    val pagedList = repoResult.pagedList
    val refreshState = repoResult.refreshState
    val loadMoreState = repoResult.networkState


    fun retry(){
        repoResult.retry.invoke()
    }

    fun refresh(){
        repoResult.refresh.invoke()
    }

}
