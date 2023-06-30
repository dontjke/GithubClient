package com.example.githubclient.mvp.model.cache

import com.example.githubclient.mvp.model.entity.GithubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IUsersCache {
    fun insertUsersToDatabase(githubUser: List<GithubUser>): Completable
    fun getUsersFromDatabase(): Single<List<GithubUser>>
}