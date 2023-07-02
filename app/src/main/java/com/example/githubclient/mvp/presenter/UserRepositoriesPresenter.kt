package com.example.githubclient.mvp.presenter

import android.util.Log
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepositories
import com.example.githubclient.mvp.model.repo.IGithubUserRepositories
import com.example.githubclient.mvp.presenter.list.IRepositoriesListPresenter
import com.example.githubclient.mvp.view.UserRepositoryView
import com.example.githubclient.mvp.view.list.IRepositoryItemView
import com.example.githubclient.navigation.IScreens
import com.example.githubclient.utils.disposeBy
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import javax.inject.Inject

class UserRepositoriesPresenter(private val user: GithubUser?) :
    MvpPresenter<UserRepositoryView>() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

    @Inject
    lateinit var retrofitGithubUserRepositoriesImpl: IGithubUserRepositories

    @Inject
    lateinit var uiScheduler: Scheduler

    private var compositeDisposable = CompositeDisposable()

    class UserRepositoriesListPresenter : IRepositoriesListPresenter {

        val userRepositories = mutableListOf<GithubUserRepositories>()

        override var itemClickListener: ((IRepositoryItemView) -> Unit)? = null
        override fun getCount() = userRepositories.size

        override fun bindView(view: IRepositoryItemView) {
            val repository = userRepositories[view.pos]
            view.setName(repository.name)
        }
    }

    val userRepositoryListPresenter = UserRepositoriesListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        user?.let { viewState.loadAvatarAndLogin(it) }
        loadData()
        user?.let { viewState.init(it) }
        userRepositoryListPresenter.itemClickListener = {
            router.navigateTo(screens.forks(userRepositoryListPresenter.userRepositories[it.pos]))
        }
    }

    private fun loadData() {
        user?.let { user ->
            retrofitGithubUserRepositoriesImpl.getUserRepositories(user)
                .observeOn(uiScheduler)
                .subscribe({ repos ->
                    userRepositoryListPresenter.userRepositories.addAll(repos)
                    viewState.updateList()
                }, {
                    Log.e("@@@", "Repository Something went wrong")
                }).disposeBy(compositeDisposable)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}
