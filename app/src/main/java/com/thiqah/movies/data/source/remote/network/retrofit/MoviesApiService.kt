package com.thiqah.movies.data.source.remote.network.retrofit

import com.thiqah.movies.data.source.remote.model.post.Movie
import io.reactivex.Observable
import retrofit2.http.GET

interface MoviesApiService {

    @GET("v2/5d398ad22f000064006ebc0b")
    fun getMovies(): Observable<List<Movie>>

}