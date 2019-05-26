package io.ahmed56734.movies.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import io.ahmed56734.movies.data.local.LocalDataSource
import io.ahmed56734.movies.data.models.Movie
import io.ahmed56734.movies.data.remote.RemoteDataSource
import io.ahmed56734.movies.util.NetworkState
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.math.round

class PopularMoviesBoundaryCallback(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val networkPageSize: Int
) : PagedList.BoundaryCallback<Movie>(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private val TAG = PopularMoviesBoundaryCallback::class.java.simpleName
    private var page: Int = 1

    init {
        runBlocking {
            page = round(localDataSource.getMoviesCount().toDouble() / networkPageSize).toInt() + 1
        }
    }


    val initialLoad: MutableLiveData<NetworkState> = MutableLiveData()
    val loadMoreState: MutableLiveData<NetworkState> = MutableLiveData()

    private var initialJob: Job? = null
    private var loadMoreJob: Job? = null

    private var retry: (() -> Any)? = null

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }

    override fun onZeroItemsLoaded() {
        if (initialJob?.isActive == true)
            return

        initialJob = launch(context = Dispatchers.IO) {
            initialLoad.postValue(NetworkState.LOADING)
            try {
                val response = remoteDataSource.getPopularMovies()
                if (response.isSuccessful) {
                    page = response.body()!!.page + 1
                    initialLoad.postValue(NetworkState.LOADED)
                    localDataSource.saveMovies(response.body()!!.movies)
                } else {
                    val err = response.message()
                    initialLoad.postValue(NetworkState.error(err))
                    Log.e(TAG, err)
                    retry = {
                        onZeroItemsLoaded()
                    }
                }
            } catch (e: Exception) {
                initialLoad.postValue(NetworkState.error(e.message))
                Log.e(TAG, e.message, e)
                retry = {
                    onZeroItemsLoaded()
                }
            }
        }
    }


    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        if (loadMoreJob?.isActive == true) {
            return
        }

        loadMoreJob = launch(context = Dispatchers.IO) {
            loadMoreState.postValue(NetworkState.LOADING)
            try {
                val response = remoteDataSource.getPopularMovies(page)
                if (response.isSuccessful) {
                    page++
                    initialLoad.postValue(NetworkState.LOADED)
                    localDataSource.saveMovies(response.body()!!.movies)
                } else {
                    val err = response.message()
                    initialLoad.postValue(NetworkState.error(err))
                    Log.e(TAG, err)
                    retry = {
                        onItemAtEndLoaded(itemAtEnd)
                    }
                }
            } catch (e: Exception) {
                loadMoreState.postValue(NetworkState.error(e.message))
                Log.e(TAG, e.message, e)
                retry = {
                    onItemAtEndLoaded(itemAtEnd)
                }
            }
        }
    }


}