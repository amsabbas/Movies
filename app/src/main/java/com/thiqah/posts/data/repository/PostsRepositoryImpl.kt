package com.thiqah.posts.data.repository

import com.thiqah.posts.data.source.local.PostsLocalDataSource
import com.thiqah.posts.data.source.remote.PostsRemoteDataSource
import com.thiqah.posts.data.source.remote.model.post.Post
import com.thiqah.posts.domain.repository.PostsRepository
import io.reactivex.Observable
import io.reactivex.Single

import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val postsRemoteDataSource: PostsRemoteDataSource,
    private val postsLocalDataSource: PostsLocalDataSource
) :
    PostsRepository {

    override fun getPosts(): Observable<List<Post>> {
        return postsRemoteDataSource.getPosts()
    }


    override fun getLocalPosts(): Single<List<Post>> {
        return postsLocalDataSource.getPosts()
    }

    override fun insertPosts(post: List<Post>): Observable<Boolean> {
        return postsLocalDataSource.insertPosts(post)
    }

}