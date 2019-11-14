package com.thiqah.movies.base.di

import androidx.room.Room
import com.thiqah.movies.core.db.ThiqahDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module


val appModules = module {
    single(qualifier = StringQualifier("db")) {
        Room.databaseBuilder(androidContext(), ThiqahDatabase::class.java, "postsDB").build()
    }
}

