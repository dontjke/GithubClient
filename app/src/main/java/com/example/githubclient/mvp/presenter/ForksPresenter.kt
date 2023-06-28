package com.example.githubclient.mvp.presenter

import com.example.githubclient.mvp.model.entity.GithubUserRepository
import com.example.githubclient.mvp.view.ForksView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class ForksPresenter(
    private val router: Router
) : MvpPresenter<ForksView>() {

    fun show(repository: GithubUserRepository) {
        viewState.showNumberOfForks(repository.forksCount.toString())
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}