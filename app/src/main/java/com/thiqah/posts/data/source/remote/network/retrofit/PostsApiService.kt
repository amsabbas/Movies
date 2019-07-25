package com.thiqah.posts.data.source.remote.network.retrofit

import com.thiqah.posts.data.source.remote.model.post.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface PostsApiService {

    @GET("v2/5d398ad22f000064006ebc0b")
    fun getPosts(): Observable<List<Post>>

}