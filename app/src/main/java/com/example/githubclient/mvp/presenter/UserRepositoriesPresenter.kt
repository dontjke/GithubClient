package com.example.githubclient.mvp.presenter

import android.util.Log
import com.example.githubclient.mvp.model.api.ApiHolder.api
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepositories
import com.example.githubclient.mvp.model.repo.IGithubUserRepositories
import com.example.githubclient.mvp.presenter.list.IRepositoryListPresenter
import com.example.githubclient.mvp.view.UserRepositoryView
import com.example.githubclient.mvp.view.list.IRepositoryItemView
import com.example.githubclient.navigation.IScreens
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter

class UserRepositoriesPresenter(
    private val user: GithubUser?,
    private val router: Router,
    private val screens: IScreens,
    private val githubUserRepositories: IGithubUserRepositories,
    private val uiScheduler: Scheduler

) :
    MvpPresenter<UserRepositoryView>() {

    private var disposable: Disposable? = null

    class UserRepositoryListPresenter : IRepositoryListPresenter {

        val userRepositories = mutableListOf<GithubUserRepositories>()

        override var itemClickListener: ((IRepositoryItemView) -> Unit)? = null
        override fun getCount() = userRepositories.size

        override fun bindView(view: IRepositoryItemView) {
            val repository = userRepositories[view.pos]
            view.setName(repository.name)
        }
    }

    val userRepositoryListPresenter = UserRepositoryListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData()
        user?.let { viewState.init(it) }
        userRepositoryListPresenter.itemClickListener = {
            router.navigateTo(screens.forks(userRepositoryListPresenter.userRepositories[it.pos]))
        }
    }
    private fun loadData() {
        disposable = user?.let { user ->
            githubUserRepositories.getUserRepositories(user)
                .observeOn(uiScheduler)
                .subscribe({repos ->
                    userRepositoryListPresenter.userRepositories.addAll(repos)
                    viewState.updateList()
                }, {
                    Log.e("@@@", "Repository Something went wrong")
                })
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}
