package com.thiqah.posts.data.source.local

import com.thiqah.posts.data.source.local.model.PostsDao
import com.thiqah.posts.data.source.remote.model.post.Post
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class PostsLocalDataSource @Inject constructor(private val postsDao: PostsDao) {

    fun getPosts(): Single<List<Post>> {
        return postsDao.getAllPosts()
    }

    fun insertPosts(posts: List<Post>): Observable<Boolean> {
        return Observable.fromCallable {
            postsDao.refreshPosts(posts)
            true
        }
    }
}