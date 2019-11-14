package com.thiqah.movies.domain.repository

import com.thiqah.movies.data.source.remote.model.post.Movie
import io.reactivex.Observable
import io.reactivex.Single


interface MoviesRepository {
    fun getRemoteMovies(): Observable<List<Movie>>

    fun getLocalMovies(): Single<List<Movie>>

    fun insertMovies(post: List<Movie>): Observable<Boolean>

}