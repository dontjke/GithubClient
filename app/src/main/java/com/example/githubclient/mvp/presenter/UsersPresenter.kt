package com.example.githubclient.mvp.presenter

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.repo.IGithubUsersRepository
import com.example.githubclient.mvp.presenter.list.IUserListPresenter
import com.example.githubclient.mvp.view.UsersView
import com.example.githubclient.mvp.view.list.UserItemView
import com.example.githubclient.navigation.IScreens
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter

class UsersPresenter(
    private val uiScheduler: Scheduler,
    private val usersRepository: IGithubUsersRepository,
    private val router: Router,
    private val screens: IScreens
) :
    MvpPresenter<UsersView>() {

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size
        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            user.login.let {
                view.setLogin(it)
            }
        }
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        usersListPresenter.itemClickListener = { itemView ->
            //TODO
        }
    }

    private fun loadData() {
        usersRepository.getUsers()
            .observeOn(uiScheduler)
            .subscribe({ repos ->
                usersListPresenter.users.addAll(repos)
                viewState.updateList()
            })
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}
