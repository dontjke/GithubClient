package com.example.githubclient.mvp.model.repo.retrofit

import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.repo.IGithubUsersRepository
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepository(private val api: IDataSource) : IGithubUsersRepository {
    override fun getUsers() = api.getUsers().subscribeOn(Schedulers.io())
}
