package com.thiqah.movies.data.source.local

import com.thiqah.movies.core.db.ThiqahDatabase
import com.thiqah.movies.data.source.remote.model.post.Movie
import io.reactivex.Observable
import io.reactivex.Single

class MoviesLocalDataSource constructor(private val db: ThiqahDatabase) {

    fun getMovies(): Single<List<Movie>> {
        return db.postsDao().getAllMovies()
    }

    fun insertMovie(posts: List<Movie>): Observable<Boolean> {
        return Observable.fromCallable {
            db.postsDao().refreshPosts(posts)
            true
        }
    }
}