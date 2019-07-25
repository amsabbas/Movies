package com.thiqah.posts.base.di

import com.thiqah.posts.presentation.di.PostsModule
import com.thiqah.posts.presentation.view.activity.PostsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [PostsModule::class])
    abstract fun bindPostsActivity(): PostsActivity
}

