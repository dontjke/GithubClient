package com.example.githubclient.di.module

import androidx.room.Room
import com.example.githubclient.App
import com.example.githubclient.mvp.model.cache.IRepositoriesCache
import com.example.githubclient.mvp.model.cache.IUsersCache
import com.example.githubclient.mvp.model.cache.RoomGithubUserRepositoriesCacheImpl
import com.example.githubclient.mvp.model.cache.RoomGithubUsersCacheImpl
import com.example.githubclient.mvp.model.entity.room.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun database(app: App): Database = Room.databaseBuilder(
        app, Database::class.java, Database.DB_NAME
    ).build()

    @Singleton
    @Provides
    fun usersCache(database: Database): IUsersCache = RoomGithubUsersCacheImpl(database)

    @Singleton
    @Provides
    fun repositoriesCache(database: Database): IRepositoriesCache =
        RoomGithubUserRepositoriesCacheImpl(database)
}