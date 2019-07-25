package com.thiqah.posts.domain.interactor

import com.thiqah.posts.data.source.remote.model.post.Post
import com.thiqah.posts.domain.repository.PostsRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val postsRepository: PostsRepository) {
    fun getPosts(): Observable<List<Post>> {
        return postsRepository.getPosts()
    }

    fun getLocalPosts(): Single<List<Post>> {
        return postsRepository.getLocalPosts()
    }

    fun insertPosts(post: List<Post>): Observable<Boolean> {
        return postsRepository.insertPosts(post)
    }
}