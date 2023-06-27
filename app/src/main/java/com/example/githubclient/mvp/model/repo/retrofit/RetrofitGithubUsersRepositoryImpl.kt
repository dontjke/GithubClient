package com.example.githubclient.mvp.model.repo.retrofit

import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import com.example.githubclient.mvp.model.repo.IGithubUsersRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepositoryImpl(private val api: IDataSource) : IGithubUsersRepository {
    override fun getUsers(): Single<List<GithubUser>> = api.getUsers().subscribeOn(Schedulers.io())
    override fun getUserRepository(reposUrl: String): Single<List<GithubUserRepository>> =
        api.getRepository(reposUrl).subscribeOn(Schedulers.io())
}
