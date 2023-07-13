package com.example.githubclient.mvp.model.cache.repo

import com.example.githubclient.mvp.model.entity.GithubFollower
import com.example.githubclient.mvp.model.entity.GithubUser
import io.reactivex.rxjava3.core.Single

interface IGithubFollowersRepository {
    fun getFollowers(user: GithubUser): Single<List<GithubFollower>>
}