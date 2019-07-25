package com.thiqah.posts.base.di


import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.thiqah.posts.core.qualifier.DatabaseInfo
import com.thiqah.posts.core.db.ThiqahDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return "postsDB"
    }

    @Provides
    @Singleton
    fun provideTooliDatabase(@DatabaseInfo dbName: String, context: Context): ThiqahDatabase {
        return Room.databaseBuilder(context, ThiqahDatabase::class.java, dbName).fallbackToDestructiveMigration()
            .build()
    }


}
