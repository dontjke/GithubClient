package com.example.githubclient.mvp.model.cache

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.entity.room.RoomGithubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RoomGithubUsersCacheImpl(private val database: Database) : IUsersCache {
    override fun insertUsersToDatabase(githubUser: List<GithubUser>): Completable {
        return database.userDao.insert(githubUser.map { user ->
            RoomGithubUser(
                user.id,
                user.login,
                user.avatarUrl,
                user.reposUrl,
                user.followersUrl
            )
        }).subscribeOn(Schedulers.io())
    }

    override fun getUsersFromDatabase(): Single<List<GithubUser>> {
        return database.userDao.getAll().map {
            it.map { roomUser ->
                GithubUser(
                    roomUser.id,
                    roomUser.login,
                    roomUser.avatarUrl,
                    roomUser.repositoryUrl
                )
            }
        }.subscribeOn(Schedulers.io())
    }
}