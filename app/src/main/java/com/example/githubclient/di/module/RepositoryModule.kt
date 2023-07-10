package com.example.githubclient.di.module

import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.cache.IRepositoriesCache
import com.example.githubclient.mvp.model.cache.IUsersCache
import com.example.githubclient.mvp.model.network.INetworkStatus
import com.example.githubclient.mvp.model.repo.IGithubUserRepositories
import com.example.githubclient.mvp.model.repo.IGithubUsersRepository
import com.example.githubclient.mvp.model.repo.retrofit.GithubUserRepositoriesImpl
import com.example.githubclient.mvp.model.repo.retrofit.GithubUsersRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun usersRepository(
        api: IDataSource,
        networkStatus: INetworkStatus,
        cache: IUsersCache
    ): IGithubUsersRepository = GithubUsersRepositoryImpl(api, networkStatus, cache)

    @Singleton
    @Provides
    fun userRepositories(
        api: IDataSource,
        networkStatus: INetworkStatus,
        cache: IRepositoriesCache
    ): IGithubUserRepositories = GithubUserRepositoriesImpl(api, networkStatus, cache)
}