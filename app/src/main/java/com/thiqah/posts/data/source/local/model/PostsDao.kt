package com.thiqah.posts.data.source.local.model

import android.arch.persistence.room.*
import com.thiqah.posts.data.source.remote.model.post.Post
import io.reactivex.Single

@Dao
interface PostsDao {

    @Query("select COUNT(*) FROM  posts")
    fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(posts: List<Post>)

    @Query("Select * from posts")
    fun getAllPosts(): Single<List<Post>>

    @Query("delete from posts")
    fun deleteAllPosts()

    @Transaction
    fun refreshPosts(posts: List<Post>) {
        deleteAllPosts()
        insertPosts(posts)
    }
}