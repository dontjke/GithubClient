package com.example.githubclient.di.user.module

import com.example.githubclient.App
import com.example.githubclient.di.user.UserScope
import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.cache.IUsersCache
import com.example.githubclient.mvp.model.cache.RoomGithubUsersCacheImpl
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.network.INetworkStatus
import com.example.githubclient.mvp.model.repo.IGithubUsersRepository
import com.example.githubclient.mvp.model.repo.retrofit.GithubUsersRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class UserModule {


    @Provides
    fun usersCache(database: Database): IUsersCache = RoomGithubUsersCacheImpl(database)

    @UserScope
    @Provides
    fun usersRepository(
        api: IDataSource,
        networkStatus: INetworkStatus,
        cache: IUsersCache
    ): IGithubUsersRepository = GithubUsersRepositoryImpl(api, networkStatus, cache)

    @UserScope
    @Provides
    open fun scopeContainer(app: App): IUserScopeContainer = app
}