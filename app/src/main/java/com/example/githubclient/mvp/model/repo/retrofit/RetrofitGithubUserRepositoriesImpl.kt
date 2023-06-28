package com.example.githubclient.mvp.model.repo.retrofit

import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepositories
import com.example.githubclient.mvp.model.entity.room.Database

import com.example.githubclient.mvp.model.entity.room.RoomGithubUserRepositories
import com.example.githubclient.mvp.model.network.INetworkStatus
import com.example.githubclient.mvp.model.repo.IGithubUserRepositories
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUserRepositoriesImpl(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val database: Database
) : IGithubUserRepositories {

    override fun getUserRepositories(user: GithubUser): Single<List<GithubUserRepositories>> =
        networkStatus.isOnlineSingle()
            .flatMap { isOnline ->
                if (isOnline) {
                    user.reposUrl?.let { url ->
                        api.getRepositories(url)
                            .flatMap { repositories ->
                                Single.fromCallable {
                                    val roomUser = user.login.let {
                                        database.userDao.findByLogin(it)
                                    } ?: throw RuntimeException("No such user in cache")
                                    val roomRepos = repositories.map {
                                        RoomGithubUserRepositories(
                                            it.id, it.name, it.forksCount,
                                            roomUser.id
                                        )
                                    }
                                    database.repositoryDao.insert(roomRepos)
                                    repositories
                                }
                            }
                    }
                        ?: Single.error<List<GithubUserRepositories>>(
                            RuntimeException("User has no repos url")
                        )
                            .subscribeOn(Schedulers.io())

                } else {
                    Single.fromCallable {
                        val roomUser = user.login.let { database.userDao.findByLogin(it) }
                            ?: throw RuntimeException("No such user in cache")
                        database.repositoryDao.findForUser(roomUser.id).map {
                            GithubUserRepositories(it.id, it.name, it.forksCount)
                        }
                    }


                }
            }.subscribeOn(Schedulers.io())


}