package com.example.githubclient.mvp.model.cache

import com.example.githubclient.mvp.model.entity.GithubFollower
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepositories
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.entity.room.RoomGithubFollower
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RoomGithubFollowersCacheImpl(private val database: Database) : IGithubFollowersCache {


    override fun insertFollowersToDatabase(
        githubUser: GithubUser,
        githubUserFollowers: List<GithubFollower>
    ): Completable {
        return database.followerDao.insert(githubUserFollowers.map { roomUserFollowers ->
            RoomGithubFollower(
                roomUserFollowers.id,
                roomUserFollowers.login,
                roomUserFollowers.avatarUrl,
                githubUser.id
            )
        }).subscribeOn(Schedulers.io())
    }

    override fun getFollowersFromDatabase(user: GithubUser): Single<List<GithubFollower>> {
        return database.followerDao.findForUser(user.id).map {
            it.map { roomUserFollowers ->
                GithubFollower(
                    roomUserFollowers.id,
                    roomUserFollowers.login,
                    roomUserFollowers.avatarUrl
                )
            }
        }.subscribeOn(Schedulers.io())
    }
}