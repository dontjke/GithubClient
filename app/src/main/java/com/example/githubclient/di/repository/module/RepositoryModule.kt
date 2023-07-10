package com.example.githubclient.di.repository.module

import com.example.githubclient.App
import com.example.githubclient.di.repository.RepositoryScope
import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.cache.IRepositoriesCache
import com.example.githubclient.mvp.model.cache.RoomGithubUserRepositoriesCacheImpl
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.network.INetworkStatus
import com.example.githubclient.mvp.model.repo.IGithubUserRepositories
import com.example.githubclient.mvp.model.repo.retrofit.GithubUserRepositoriesImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {


    @Provides
    fun repositoriesCache(database: Database): IRepositoriesCache =
        RoomGithubUserRepositoriesCacheImpl(database)

    @RepositoryScope
    @Provides
    fun userRepositories(
        api: IDataSource,
        networkStatus: INetworkStatus,
        cache: IRepositoriesCache
    ): IGithubUserRepositories = GithubUserRepositoriesImpl(api, networkStatus, cache)

    @RepositoryScope
    @Provides
    open fun scopeContainer(app: App): IRepositoryScopeContainer = app
}