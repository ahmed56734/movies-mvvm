package io.ahmed56734.movies.data.repository

import io.ahmed56734.movies.data.remote.RemoteDataSource

class DetailsRepository(private val remoteDataSource: RemoteDataSource) {


    suspend fun getAllMovieDetails(id: Long) = remoteDataSource.getMovieDetails(id)

}