package com.thiqah.movies.presentation.viewmodel

import com.thiqah.movies.core.utility.ThiqahException
import com.thiqah.movies.data.source.remote.model.post.Movie
import com.thiqah.movies.domain.interactor.GetMoviesUseCase
import com.thiqah.movies.presentation.model.BaseViewModel
import com.thiqah.movies.presentation.model.ObservableResourceMObservers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MoviesViewModel constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : BaseViewModel() {

    val moviesObservableResource by lazy { ObservableResourceMObservers<List<Movie>>() }

    val localMoviesObservableResource by lazy { ObservableResourceMObservers<List<Movie>>() }

    fun getMovies(): ObservableResourceMObservers<List<Movie>> {

        addDisposable(
            getMoviesUseCase.getMovies()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { moviesObservableResource.loading.postValue(true) }
                .subscribe({
                    it?.let {
                        if (it.isNotEmpty())
                            saveMovies(it)
                    }
                    moviesObservableResource.postValue(it)
                    moviesObservableResource.loading.postValue (false)
                }, {
                    moviesObservableResource.loading.postValue(false)
                    moviesObservableResource.error.postValue(it as ThiqahException?)
                })
        )

        return moviesObservableResource
    }


    fun getLocalMovies(): ObservableResourceMObservers<List<Movie>> {

        addDisposable(
            getMoviesUseCase.getLocalMovies()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { localMoviesObservableResource.loading.postValue(true) }
                .subscribe({
                    localMoviesObservableResource.postValue(it)
                    localMoviesObservableResource.loading.postValue( false)
                }, {
                    localMoviesObservableResource.loading.postValue( false)
                })
        )

        return localMoviesObservableResource
    }

    //coroutines
    private fun saveMovies(movie: List<Movie>) {
        GlobalScope.launch(Dispatchers.IO) {
            val movies = async  { getMoviesUseCase.insertMovies(movie) }
            addDisposable(movies.await().subscribe())
        }
    }

}