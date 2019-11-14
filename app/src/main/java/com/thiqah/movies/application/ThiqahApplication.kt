package com.thiqah.movies.application

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.thiqah.movies.base.di.appModules
import com.thiqah.movies.presentation.di.moviesModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class ThiqahApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // declare used Android context
            androidContext(this@ThiqahApplication)
            // declare modules
            modules(listOf(appModules, moviesModules))
        }


    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
