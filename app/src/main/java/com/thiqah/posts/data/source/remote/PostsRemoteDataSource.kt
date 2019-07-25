package com.thiqah.posts.data.source.remote


import com.thiqah.posts.data.source.remote.network.retrofit.PostsApiService
import javax.inject.Inject

class PostsRemoteDataSource @Inject constructor(private val postsApiService: PostsApiService) {
    fun getPosts() = postsApiService.getPosts()
}