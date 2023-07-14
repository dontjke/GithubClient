package com.example.githubclient.mvp.model.cache.repo.retrofit

import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.cache.IUsersCache
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.network.INetworkStatus
import com.example.githubclient.mvp.model.cache.repo.IGithubUsersRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubUsersRepositoryImpl(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val cache: IUsersCache
) : IGithubUsersRepository {
    override fun getUsers(): Single<List<GithubUser>> = networkStatus.isOnlineSingle()
        .flatMap { isOnline ->
            if (isOnline) {
                api.getUsers().flatMap { users ->
                    cache.insertUsersToDatabase(users).andThen(Single.just(users))
                }
            } else {
                cache.getUsersFromDatabase()
            }
        }.subscribeOn(Schedulers.io())
}
