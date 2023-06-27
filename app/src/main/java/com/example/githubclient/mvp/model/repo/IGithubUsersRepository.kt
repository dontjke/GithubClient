package com.example.githubclient.mvp.model.repo

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import io.reactivex.rxjava3.core.Single

interface IGithubUsersRepository {
    fun getUsers(): Single<List<GithubUser>>
    fun getUserRepository(reposUrl: String): Single<List<GithubUserRepository>>
}