package com.example.githubclient.mvp.model.cache

import com.example.githubclient.mvp.model.entity.GithubFollower
import com.example.githubclient.mvp.model.entity.GithubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IGithubFollowersCache {
    fun getFollowersFromDatabase(user: GithubUser): Single<List<GithubFollower>>
    fun insertFollowersToDatabase(
        githubUser: GithubUser,
        githubUserFollowers: List<GithubFollower>
    ): Completable
}