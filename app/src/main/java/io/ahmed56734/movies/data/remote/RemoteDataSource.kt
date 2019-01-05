package io.ahmed56734.movies.data.remote

import io.ahmed56734.movies.data.models.MovieDetails
import io.ahmed56734.movies.data.models.MoviesResponse
import retrofit2.Response

class RemoteDataSource(private val moviesApi: MoviesApi) {

    suspend fun getPopularMovies(page: Int = 1): Response<MoviesResponse> {
        return moviesApi.discover(SortType.PopularityDesc.value, page).await()
    }

    suspend fun getMovieDetails(movieId: Long): Response<MovieDetails> {
        return moviesApi.getMovie(movieId).await()
    }
}