package com.example.githubclient.mvp.model

import io.reactivex.rxjava3.core.Observable

class GithubUsersRepo {
    private val repositories = listOf(
        GithubUser("login1"),
        GithubUser("login2"),
        GithubUser("login3"),
        GithubUser("login4"),
        GithubUser("login5")
    )
    fun fromIterable(): Observable<GithubUser> {
        return Observable.fromIterable(repositories)
    }

    fun getUsers(): List<GithubUser> {
        return repositories
    }
}