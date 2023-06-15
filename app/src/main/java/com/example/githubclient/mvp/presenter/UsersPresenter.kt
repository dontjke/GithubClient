package com.example.githubclient.mvp.presenter

import com.example.githubclient.mvp.model.GithubUser
import com.example.githubclient.mvp.model.GithubUsersRepo
import com.example.githubclient.mvp.presenter.list.IUserListPresenter
import com.example.githubclient.mvp.view.UsersView
import com.example.githubclient.mvp.view.list.UserItemView
import com.example.githubclient.navigation.IScreens
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter

class UsersPresenter(val usersRepo: GithubUsersRepo, val router: Router,val screens: IScreens) :
    MvpPresenter<UsersView>() {

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size
        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }
    }

    val usersListPresenter = UsersListPresenter()
    private val githubUserObserver = object : Observer<GithubUser> {
        var disposable: Disposable? = null
        override fun onSubscribe(d: Disposable) {
            disposable = d
            println("onSubscribe")
        }

        override fun onError(e: Throwable) {
            println("onError: ${e.message}")
        }

        override fun onComplete() {
            println("onComplete")
        }

        override fun onNext(s: GithubUser) {
            println("onNext: $s")
            /*usersListPresenter.users.add(s)*/
        }
    }

    private fun execFromIterable() {
        usersRepo.fromIterable()
            .subscribe(githubUserObserver)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        usersListPresenter.itemClickListener = { itemView ->
            router.navigateTo(screens.user(itemView.pos))
        }
    }
    private fun loadData() {
        execFromIterable()
        viewState.updateList()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}
