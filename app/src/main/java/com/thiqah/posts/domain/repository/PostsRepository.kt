package com.thiqah.posts.domain.repository

import com.thiqah.posts.data.source.remote.model.post.Post
import io.reactivex.Observable
import io.reactivex.Single


interface PostsRepository {
    fun getPosts(): Observable<List<Post>>

    fun getLocalPosts(): Single<List<Post>>

    fun insertPosts(post: List<Post>): Observable<Boolean>

}