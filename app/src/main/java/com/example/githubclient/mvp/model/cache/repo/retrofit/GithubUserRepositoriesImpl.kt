package com.example.githubclient.mvp.model.cache.repo.retrofit

import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.cache.IRepositoriesCache
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepositories
import com.example.githubclient.mvp.model.network.INetworkStatus
import com.example.githubclient.mvp.model.cache.repo.IGithubUserRepositories
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubUserRepositoriesImpl(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val cache: IRepositoriesCache
) : IGithubUserRepositories {

    override fun getUserRepositories(user: GithubUser): Single<List<GithubUserRepositories>> =
        networkStatus.isOnlineSingle()
            .flatMap { isOnline ->
                if (isOnline) {
                    user.reposUrl?.let { url ->
                        api.getRepositories(url)
                            .flatMap { repositories ->
                                cache.insertRepositoriesToDatabase(repositories, user)
                                    .andThen(Single.just(repositories))
                            }
                    }
                        ?: Single.error(RuntimeException("User has no repos url"))
                } else {
                    cache.getRepositoriesFromDatabase(user)
                }
            }.subscribeOn(Schedulers.io())


}