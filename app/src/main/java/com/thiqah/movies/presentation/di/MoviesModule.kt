package com.thiqah.movies.presentation.di

import com.thiqah.movies.core.utility.ServiceGenerator
import com.thiqah.movies.data.repository.MoviesRepositoryImpl
import com.thiqah.movies.data.source.local.MoviesLocalDataSource
import com.thiqah.movies.data.source.remote.MoviesRemoteDataSource
import com.thiqah.movies.data.source.remote.network.retrofit.MoviesApiService
import com.thiqah.movies.domain.interactor.GetMoviesUseCase
import com.thiqah.movies.domain.repository.MoviesRepository
import com.thiqah.movies.presentation.view.adapter.MoviesAdapter
import com.thiqah.movies.presentation.viewmodel.MoviesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module


val moviesModules = module {
    factory { ServiceGenerator().createService(MoviesApiService::class.java) as MoviesApiService }
    factory { MoviesRepositoryImpl(get(), get()) as  MoviesRepository }
    factory { MoviesLocalDataSource(get(StringQualifier("db"))) }
    factory { MoviesRemoteDataSource(get()) }
    factory { MoviesAdapter() }
    factory { GetMoviesUseCase(get()) }
    viewModel { MoviesViewModel(get()) }
}

