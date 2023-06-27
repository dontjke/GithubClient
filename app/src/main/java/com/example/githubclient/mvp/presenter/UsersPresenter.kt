package com.example.githubclient.mvp.presenter

import android.util.Log
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.repo.IGithubUsersRepository
import com.example.githubclient.mvp.presenter.list.IUserListPresenter
import com.example.githubclient.mvp.view.UsersView
import com.example.githubclient.mvp.view.list.IUserItemView
import com.example.githubclient.navigation.IScreens
import com.example.githubclient.utils.disposeBy
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter

class UsersPresenter(
    private val uiScheduler: Scheduler,
    private val usersRepository: IGithubUsersRepository,
    private val router: Router,
    private val screens: IScreens
) :
    MvpPresenter<UsersView>() {

    private var compositeDisposable = CompositeDisposable()

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((IUserItemView) -> Unit)? = null

        override fun getCount() = users.size
        override fun bindView(view: IUserItemView) {
            val user = users[view.pos]
            user.login.let {
                view.setLogin(it)
            }
            user.avatarUrl?.let {
                view.loadAvatar(it)
            }
        }
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        usersListPresenter.itemClickListener = {
            router.navigateTo(screens.userRepositories(usersListPresenter.users[it.pos]))
        }
    }

    private fun loadData() {
        usersRepository.getUsers()
            .observeOn(uiScheduler)
            .subscribe({ repos ->
                usersListPresenter.users.addAll(repos)
                viewState.updateList()
            }, {
                Log.e("@@@", "Something went wrong")
            }).disposeBy(compositeDisposable)
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
