package io.ahmed56734.movies.data.remote

import io.ahmed56734.movies.data.models.MovieDetails
import io.ahmed56734.movies.data.models.MoviesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("discover/movie")
    fun discover(
        @Query("sort_by") sort: String,
        @Query("page") page: Int
    ): Deferred<Response<MoviesResponse>>


    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") id: Long): Deferred<Response<MovieDetails>>
}


enum class SortType(val value: String) {
    PopularityDesc("popularity.desc"),
    ReleaseDateDesc("release_date.desc")
}