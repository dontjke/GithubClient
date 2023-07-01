package com.example.githubclient.mvp.presenter

import com.example.githubclient.mvp.model.entity.GithubUserRepositories
import com.example.githubclient.mvp.view.ForksView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class ForksPresenter : MvpPresenter<ForksView>() {
    @Inject
    lateinit var router: Router

    fun show(repository: GithubUserRepositories) {
        viewState.showNumberOfForks(repository.forksCount.toString())
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}