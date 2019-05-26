package io.ahmed56734.movies.data.remote

import io.ahmed56734.movies.data.models.CreditsResponse
import io.ahmed56734.movies.data.models.MovieDetails
import io.ahmed56734.movies.data.models.MoviesResponse
import retrofit2.Response

class RemoteDataSource(private val moviesApi: MoviesApi) {

    suspend fun getPopularMovies(page: Int = 1): Response<MoviesResponse> {
        return moviesApi.discoverAsync(SortType.PopularityDesc.value, page).await()
    }

    suspend fun getMovieDetails(movieId: Long): Pair<Response<MovieDetails>, Response<CreditsResponse>> {
        return moviesApi.getMovieAsync(movieId).await() to moviesApi.getCreditsAsync(movieId).await()
    }

    suspend fun searchMovies(query: String, page: Int) = moviesApi.searchAsync(query = query, page = page).await()
}