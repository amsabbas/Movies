package com.thiqah.movies.data.repository

import com.thiqah.movies.data.source.local.MoviesLocalDataSource
import com.thiqah.movies.data.source.remote.MoviesRemoteDataSource
import com.thiqah.movies.data.source.remote.model.post.Movie
import com.thiqah.movies.domain.repository.MoviesRepository
import io.reactivex.Observable
import io.reactivex.Single


class MoviesRepositoryImpl constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource: MoviesLocalDataSource
) :
    MoviesRepository {

    override fun getRemoteMovies(): Observable<List<Movie>> {
        return moviesRemoteDataSource.getMovies()
    }


    override fun getLocalMovies(): Single<List<Movie>> {
        return moviesLocalDataSource.getMovies()
    }

    override suspend fun insertMovies(post: List<Movie>) {
         moviesLocalDataSource.insertMovie(post)
    }
}