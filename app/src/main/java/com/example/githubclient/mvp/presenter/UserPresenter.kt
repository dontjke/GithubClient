package com.example.githubclient.mvp.presenter

import com.example.githubclient.mvp.model.entity.GithubUsersRepo
import com.example.githubclient.mvp.view.UserView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class UserPresenter(val usersRepo: GithubUsersRepo, val router: Router) :
    MvpPresenter<UserView>() {

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}
