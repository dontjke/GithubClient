package com.example.githubclient.mvp.view

import com.example.githubclient.mvp.model.entity.GithubUser
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserRepositoryView : MvpView {
    fun init(user: GithubUser)
    fun updateList()
    fun loadAvatarAndLogin(user: GithubUser)
}