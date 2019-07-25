package com.thiqah.posts.presentation.di

import com.thiqah.posts.core.db.ThiqahDatabase
import com.thiqah.posts.core.utility.ServiceGenerator
import com.thiqah.posts.data.repository.PostsRepositoryImpl
import com.thiqah.posts.data.source.local.PostsLocalDataSource
import com.thiqah.posts.data.source.remote.PostsRemoteDataSource
import com.thiqah.posts.data.source.remote.network.retrofit.PostsApiService
import com.thiqah.posts.domain.interactor.GetPostsUseCase
import com.thiqah.posts.domain.repository.PostsRepository
import com.thiqah.posts.presentation.view.adapter.PostsAdapter
import com.thiqah.posts.presentation.viewmodel.PostsViewModel
import dagger.Module
import dagger.Provides


@Module
class PostsModule {

    @Provides
    fun providePostsServiceAPIs(): PostsApiService =
        ServiceGenerator().createService(PostsApiService::class.java)

    @Provides
    fun providePostsRemoteDataSource(
        postsApiService: PostsApiService
    ): PostsRemoteDataSource =
        PostsRemoteDataSource(postsApiService)


    @Provides
    fun providePostsLocalDataSource(
        db: ThiqahDatabase
    ): PostsLocalDataSource =
        PostsLocalDataSource(db.postsDao())


    @Provides
    fun providePostsRepository(
        remoteDataSource: PostsRemoteDataSource, localDataSource: PostsLocalDataSource
    ): PostsRepository =
        PostsRepositoryImpl(remoteDataSource, localDataSource)


    @Provides
    fun providePostsUseCase(postsRepository: PostsRepository): GetPostsUseCase =
        GetPostsUseCase(postsRepository)

    @Provides
    fun providePostsViewModel(
        postsUseCase: GetPostsUseCase
    ): PostsViewModel =
        PostsViewModel(postsUseCase)

    @Provides
    fun providePostsAdapter(): PostsAdapter {
        return PostsAdapter()
    }


}