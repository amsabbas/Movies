package com.thiqah.posts.core.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.thiqah.posts.data.source.local.model.PostsDao
import com.thiqah.posts.data.source.remote.model.post.Post

@Database(
        entities = [Post::class], version = 9, exportSchema = false)
abstract class ThiqahDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao
}
