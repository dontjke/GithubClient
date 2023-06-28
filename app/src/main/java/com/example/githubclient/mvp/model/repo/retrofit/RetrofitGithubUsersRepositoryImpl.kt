package com.example.githubclient.mvp.model.repo.retrofit

import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.entity.room.RoomGithubUser
import com.example.githubclient.mvp.model.network.INetworkStatus
import com.example.githubclient.mvp.model.repo.IGithubUsersRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepositoryImpl(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val database: Database
) : IGithubUsersRepository {
    override fun getUsers(): Single<List<GithubUser>> = networkStatus.isOnlineSingle()
        .flatMap { isOnline ->
            if (isOnline) {
                api.getUsers().flatMap { users ->
                    Single.fromCallable {
                        val roomUsers = users.map { user ->
                            RoomGithubUser(
                                user.id ?: "",
                                user.login ?: "",
                                user.avatarUrl ?: "",
                                user.reposUrl ?: ""
                            )
                        }
                        database.userDao.insert(roomUsers)
                        users
                    }
                }
            } else {
                Single.fromCallable {
                    database.userDao.getAll().map { roomUser ->
                        GithubUser(
                            roomUser.id,
                            roomUser.login,
                            roomUser.avatarUrl,
                            roomUser.repositoryUrl
                        )
                    }
                }

            }
        }.subscribeOn(Schedulers.io())
}
