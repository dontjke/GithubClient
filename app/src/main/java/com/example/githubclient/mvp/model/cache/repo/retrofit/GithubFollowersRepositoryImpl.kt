package com.example.githubclient.mvp.model.cache.repo.retrofit

import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.cache.IGithubFollowersCache
import com.example.githubclient.mvp.model.cache.repo.IGithubFollowersRepository
import com.example.githubclient.mvp.model.entity.GithubFollower
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.network.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubFollowersRepositoryImpl(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val cache: IGithubFollowersCache
) : IGithubFollowersRepository {

    override fun getFollowers(user: GithubUser): Single<List<GithubFollower>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                user.followersUrl?.let { url ->
                    api.getFollowers(url)
                        .flatMap { followers ->
                            cache.insertFollowersToDatabase(user, followers)
                                .toSingleDefault(followers)
                        }
                } ?: Single.error<List<GithubFollower>>(RuntimeException("User has no repos url"))
                    .subscribeOn(Schedulers.io())
            } else {
                cache.getFollowersFromDatabase(user)
            }
        }.subscribeOn(Schedulers.io())
}