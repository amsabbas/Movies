package com.thiqah.movies.data.source.remote


import com.thiqah.movies.data.source.remote.network.retrofit.MoviesApiService

class MoviesRemoteDataSource constructor(private val moviesApiService: MoviesApiService) {
    fun getMovies() = moviesApiService.getMovies()
}