package com.example.githubclient.mvp.presenter

import com.example.githubclient.mvp.model.entity.GithubUserRepositories
import com.example.githubclient.mvp.view.RepositoryView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class RepositoryPresenter(private val githubUserRepositories: GithubUserRepositories?) :
    MvpPresenter<RepositoryView>() {

    @Inject
    lateinit var router: Router

    public override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        githubUserRepositories?.let { viewState.setId(it.id) }
        githubUserRepositories?.let { viewState.setTitle(it.name) }
        viewState.setForksCount((githubUserRepositories?.forksCount).toString())
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy presenter")
    }
}