package io.ahmed56734.movies.data.repository

import androidx.paging.PagedList
import androidx.paging.toLiveData
import io.ahmed56734.movies.data.local.LocalDataSource
import io.ahmed56734.movies.util.Listing
import io.ahmed56734.movies.data.models.Movie
import io.ahmed56734.movies.data.remote.RemoteDataSource

class MoviesRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }


    fun popularMovies(): Listing<Movie> {

        val boundaryCallback = PopularMoviesBoundaryCallback(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            networkPageSize = NETWORK_PAGE_SIZE
        )

        val pageConfig = PagedList.Config.Builder()
            .setPageSize(NETWORK_PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        val livePageList = localDataSource.getPopularMoviesDataFactory().toLiveData(
            config = pageConfig,
            boundaryCallback = boundaryCallback
        )

        return Listing<Movie>(
            pagedList = livePageList,
            networkState = boundaryCallback.loadMoreState,
            refreshState = boundaryCallback.initialLoad,
            retry = {
                boundaryCallback.retryAllFailed()
            },

            refresh = {
                boundaryCallback.onZeroItemsLoaded()
            }
        )

    }

    fun getfavoriteMoviesPagedList() =
        localDataSource.getFavoriteMovies().toLiveData(pageSize = 20)


    suspend fun toggleFavorites(movie: Movie) = localDataSource.toggleFavorites(movie)

}