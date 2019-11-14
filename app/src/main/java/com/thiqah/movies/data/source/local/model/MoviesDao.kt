package com.thiqah.movies.data.source.local.model

import androidx.room.*
import com.thiqah.movies.data.source.remote.model.post.Movie
import io.reactivex.Single

@Dao
interface MoviesDao {

    @Query("select COUNT(*) FROM  movies")
    fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(posts: List<Movie>)

    @Query("Select * from movies")
    fun getAllMovies(): Single<List<Movie>>

    @Query("delete from movies")
    fun deleteAllMovies()

    @Transaction
    fun refreshPosts(posts: List<Movie>) {
        deleteAllMovies()
        insertMovie(posts)
    }
}