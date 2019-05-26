package io.ahmed56734.movies.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import io.ahmed56734.movies.data.local.LocalDataSource
import io.ahmed56734.movies.data.models.Movie
import io.ahmed56734.movies.data.models.SearchQuery
import io.ahmed56734.movies.data.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.get
import java.io.IOException

import kotlin.coroutines.CoroutineContext

class SearchRepository(private val localDataSource: LocalDataSource) {

    suspend fun addSearchQuery(searchQuery: SearchQuery) = localDataSource.addSearchQuery(searchQuery)

    fun getRecentQueries(): LiveData<List<String>> = Transformations.map(localDataSource.getRecentSearchQueries()) {
        it.map {
            it.searchValue
        }
    }

    fun getSearchDataSourceFactory(searchQuery: String) = SearchDataSourceFactory(searchQuery)
}

class SearchDataSourceFactory(private val searchQuery: String) : DataSource.Factory<Int, Movie>(), KoinComponent {
    val sourceLiveData = MutableLiveData<SearchDataSource>()
    var latestSource: SearchDataSource? = null
    override fun create(): DataSource<Int, Movie> {
        latestSource = SearchDataSource(remoteDataSource = get(), searchQuery = searchQuery)
        sourceLiveData.postValue(latestSource)
        return latestSource!!
    }
}

class SearchDataSource(private val remoteDataSource: RemoteDataSource, private val searchQuery: String) :
    PageKeyedDataSource<Int, Movie>(),
    CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        launch {
            try {
                val response = remoteDataSource.searchMovies(query = searchQuery, page = 1)
                if (response.isSuccessful) {
                    callback.onResult(response.body()?.movies ?: emptyList(), 1, 2)
                }
            } catch (e: IOException) {
                loadInitial(params, callback)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        launch {
            try {
                val page = params.key
                val response = remoteDataSource.searchMovies(query = searchQuery, page = page)
                if (response.isSuccessful) {
                    val nextPage = if (page >= response.body()?.totalPages!!) null else page + 1
                    callback.onResult(response.body()?.movies ?: emptyList(), nextPage)
                }
            } catch (e: IOException) {
                loadAfter(params, callback)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }


}

