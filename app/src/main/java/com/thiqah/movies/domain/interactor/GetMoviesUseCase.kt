package com.thiqah.movies.domain.interactor

import com.thiqah.movies.data.source.remote.model.post.Movie
import com.thiqah.movies.domain.repository.MoviesRepository
import io.reactivex.Observable
import io.reactivex.Single


class GetMoviesUseCase  constructor(private val moviesRepository: MoviesRepository) {
    fun getMovies(): Observable<List<Movie>> {
        return moviesRepository.getRemoteMovies()
    }

    fun getLocalMovies(): Single<List<Movie>> {
        return moviesRepository.getLocalMovies()
    }

    suspend fun insertMovies(movies: List<Movie>) {
         moviesRepository.insertMovies(movies)
    }
}