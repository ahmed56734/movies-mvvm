package io.ahmed56734.movies.repository.popular

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import io.ahmed56734.movies.data.local.LocalDataSource
import io.ahmed56734.movies.data.models.Movie
import io.ahmed56734.movies.data.remote.RemoteDataSource
import io.ahmed56734.movies.repository.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MoviesBoundaryCallback(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val networkPageSize: Int
) : PagedList.BoundaryCallback<Movie>() {

    private val TAG = MoviesBoundaryCallback::class.java.simpleName

    private var page = 1

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
        if(initialJob?.isActive == true)
            return

        initialJob = GlobalScope.launch(context = Dispatchers.IO) {
            initialLoad.postValue(NetworkState.LOADING)
            try {
                val response = remoteDataSource.getPopularMovies()
                if (response.isSuccessful) {
                    page++
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
        if(loadMoreJob?.isActive == true)
            return

        loadMoreJob = GlobalScope.launch(context = Dispatchers.IO) {
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