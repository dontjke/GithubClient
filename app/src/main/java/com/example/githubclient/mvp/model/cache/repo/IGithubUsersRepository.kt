package com.example.githubclient.mvp.model.cache.repo

import com.example.githubclient.mvp.model.entity.GithubUser
import io.reactivex.rxjava3.core.Single

interface IGithubUsersRepository {
    fun getUsers(): Single<List<GithubUser>>
}