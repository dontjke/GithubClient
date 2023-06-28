package com.example.githubclient.mvp.model.cache

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepositories
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IRepositoriesCache {
    fun insertRepositoriesToDatabase(
        githubUserRepositories: List<GithubUserRepositories>,
        githubUser: GithubUser
    ): Completable

    fun getRepositoriesFromDatabase(user: GithubUser): Single<List<GithubUserRepositories>>
}