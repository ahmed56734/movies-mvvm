package io.ahmed56734.movies.ui.search


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import io.ahmed56734.movies.data.models.Movie
import io.ahmed56734.movies.data.models.SearchQuery
import io.ahmed56734.movies.data.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private val queries = MutableLiveData<String>()

    val moviesPagedList: LiveData<PagedList<Movie>> =
        Transformations.switchMap(queries) { query ->
            searchRepository.getSearchDataSourceFactory(query).toLiveData(pageSize = 20, initialLoadKey = 1)
        }

    fun search(query: String) {
        queries.value = query
        addSearchQuery(query)
    }

    val recentQueries: LiveData<List<String>> = searchRepository.getRecentQueries()

    private fun addSearchQuery(query: String) = launch {
        searchRepository.addSearchQuery(SearchQuery(query))
    }

}