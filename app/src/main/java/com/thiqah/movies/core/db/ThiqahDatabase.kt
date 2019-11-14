package com.thiqah.movies.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thiqah.movies.data.source.local.model.MoviesDao
import com.thiqah.movies.data.source.remote.model.post.Movie

@Database(
        entities = [Movie::class], version = 9, exportSchema = false)
abstract class ThiqahDatabase : RoomDatabase() {
    abstract fun postsDao(): MoviesDao
}
