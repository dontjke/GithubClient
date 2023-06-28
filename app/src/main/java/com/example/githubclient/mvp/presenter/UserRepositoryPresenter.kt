package com.example.githubclient.mvp.presenter

import android.util.Log
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import com.example.githubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepositoryImpl
import com.example.githubclient.mvp.presenter.list.IRepositoryListPresenter
import com.example.githubclient.mvp.view.UserRepositoryView
import com.example.githubclient.mvp.view.list.IRepositoryItemView
import com.example.githubclient.navigation.IScreens
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter

class UserRepositoryPresenter(
    private val user: GithubUser?,
    private val router: Router,
    private val screens: IScreens,
    private val usersRepositoryImpl: RetrofitGithubUsersRepositoryImpl,
    private val uiScheduler: Scheduler

) :
    MvpPresenter<UserRepositoryView>() {

    private var disposable: Disposable? = null

    class UserRepositoryListPresenter : IRepositoryListPresenter {

        val userRepositories = mutableListOf<GithubUserRepository>()

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
        disposable = user?.reposUrl?.let { reposUrl ->
            usersRepositoryImpl.getUserRepository(reposUrl)
                .observeOn(uiScheduler)
                .subscribe({
                    userRepositoryListPresenter.userRepositories.addAll(it)
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
