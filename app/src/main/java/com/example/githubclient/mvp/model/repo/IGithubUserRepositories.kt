package com.example.githubclient.mvp.model.repo

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepositories
import io.reactivex.rxjava3.core.Single

interface IGithubUserRepositories {
    fun getUserRepositories(user: GithubUser): Single<List<GithubUserRepositories>>
}