package com.thiqah.posts.presentation.viewmodel

import com.thiqah.posts.core.utility.ThiqahException
import com.thiqah.posts.data.source.remote.model.post.Post
import com.thiqah.posts.domain.interactor.GetPostsUseCase
import com.thiqah.posts.presentation.model.BaseViewModel
import com.thiqah.posts.presentation.model.ObservableResourceMObservers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val postsUseCase: GetPostsUseCase
) : BaseViewModel() {

    val postsObservableResource by lazy { ObservableResourceMObservers<List<Post>>() }

    val localPostsObservableResource by lazy { ObservableResourceMObservers<List<Post>>() }

    fun getPosts(): ObservableResourceMObservers<List<Post>> {

        addDisposable(
            postsUseCase.getPosts()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { postsObservableResource.loading.postValue(true) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it?.let {
                        if (it.isNotEmpty())
                            savePosts(it)
                    }
                    postsObservableResource.value = it
                    postsObservableResource.loading.value = false
                }, {
                    postsObservableResource.loading.value = false
                    postsObservableResource.error.value = it as ThiqahException?
                })
        )

        return postsObservableResource
    }


    fun getLocalPosts(): ObservableResourceMObservers<List<Post>> {

        addDisposable(
            postsUseCase.getLocalPosts()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { localPostsObservableResource.loading.postValue(true) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    localPostsObservableResource.value = it
                    localPostsObservableResource.loading.value = false
                }, {
                    localPostsObservableResource.loading.value = false
                })
        )

        return localPostsObservableResource
    }


    private fun savePosts(post: List<Post>) {
        addDisposable(
            postsUseCase.insertPosts(post)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

}