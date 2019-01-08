package io.ahmed56734.movies.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import io.ahmed56734.movies.data.models.Cast
import io.ahmed56734.movies.data.models.MovieDetails
import io.ahmed56734.movies.data.repository.DetailsRepository
import io.ahmed56734.movies.util.NetworkState
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MovieDetailsViewModel(private val detailsRepository: DetailsRepository) : ViewModel(), CoroutineScope {
    private val TAG = MovieDetailsViewModel::class.java.simpleName

    private val compositeJob = Job()
    override val coroutineContext: CoroutineContext
        get() = compositeJob + Dispatchers.Main

    private val _networkStateLive: MutableLiveData<NetworkState> = MutableLiveData()
    val networkStateLive: LiveData<NetworkState>
        get() = _networkStateLive

    private val _movieDetailsLive: MutableLiveData<MovieDetails> = MutableLiveData()
    val movieDetailsLive: LiveData<MovieDetails>
        get() = _movieDetailsLive

    private val _movieCastLive: MutableLiveData<List<Cast>> = MutableLiveData()
    val movieCastLive: LiveData<List<Cast>>
        get() = _movieCastLive


    fun loadMovieDetails(movieId: Long) = launch {

        try {
            _networkStateLive.value = NetworkState.LOADING
            val result = detailsRepository.getAllMovieDetails(movieId)

            if (result.first.isSuccessful && result.second.isSuccessful) {
                _networkStateLive.value = NetworkState.LOADED

                val movieResult = result.first.body()
                val castResult = result.second.body()

                _movieDetailsLive.value = movieResult
                _movieCastLive.value = castResult!!.cast

            } else {
                _networkStateLive.value = NetworkState.error(null)
            }

        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            _networkStateLive.value = NetworkState.error(e.message)

        }
    }


    override fun onCleared() {
        super.onCleared()
        compositeJob.cancel()
    }
}
