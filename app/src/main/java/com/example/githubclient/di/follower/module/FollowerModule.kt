package com.example.githubclient.di.follower.module

import com.example.githubclient.App
import com.example.githubclient.di.follower.FollowerScope
import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.cache.IGithubFollowersCache
import com.example.githubclient.mvp.model.cache.RoomGithubFollowersCacheImpl
import com.example.githubclient.mvp.model.cache.repo.IGithubFollowersRepository
import com.example.githubclient.mvp.model.cache.repo.retrofit.GithubFollowersRepositoryImpl
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.network.INetworkStatus
import dagger.Module
import dagger.Provides

@Module
class FollowerModule {

    @Provides
    fun followersCache(dataBase: Database): IGithubFollowersCache =
        RoomGithubFollowersCacheImpl(dataBase)

    @FollowerScope
    @Provides
    fun followersRepo(
        api: IDataSource, networkStatus: INetworkStatus, cache:
        IGithubFollowersCache
    ): IGithubFollowersRepository = GithubFollowersRepositoryImpl(
        api,
        networkStatus, cache
    )

    @FollowerScope
    @Provides
    open fun scopeContainer(app: App): IFollowerScopeContainer = app
}