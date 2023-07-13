package com.example.githubclient.mvp.presenter

import android.util.Log
import com.example.githubclient.di.user.module.IUserScopeContainer
import com.example.githubclient.mvp.model.cache.repo.IGithubUsersRepository
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.presenter.list.IUserListPresenter
import com.example.githubclient.mvp.view.UsersView
import com.example.githubclient.mvp.view.list.IUserItemView
import com.example.githubclient.navigation.IScreens
import com.example.githubclient.utils.disposeBy
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import javax.inject.Inject

class UsersPresenter :
    MvpPresenter<UsersView>() {
    @Inject
    lateinit var uiScheduler: Scheduler

    @Inject
    lateinit var retrofitGithubUsersRepositoryImpl: IGithubUsersRepository

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

    @Inject
    lateinit var userScopeContainer: IUserScopeContainer

    private var compositeDisposable = CompositeDisposable()

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        private val repositoriesClickObservers = mutableListOf<(IUserItemView) -> Unit>()
        private val followersClickObservers = mutableListOf<(IUserItemView) -> Unit>()
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

        override fun onRepositoriesClicked(view: IUserItemView) {
            repositoriesClickObservers.forEach { observer ->
                observer.invoke(view)
            }
        }

        override fun onFollowersClicked(view: IUserItemView) {
            followersClickObservers.forEach { observer ->
                observer.invoke(view)
            }
        }

        fun addRepositoriesObserver(observer: (IUserItemView) -> Unit) {
            repositoriesClickObservers.add(observer)
        }

        fun addFollowersObserver(observer: (IUserItemView) -> Unit) {
            followersClickObservers.add(observer)
        }

        fun removeRepositoriesObserver(observer: (IUserItemView) -> Unit) {
            repositoriesClickObservers.remove(observer)
        }

        fun removeFollowersObserver(observer: (IUserItemView) -> Unit) {
            followersClickObservers.remove(observer)
        }
    }

    private lateinit var repositoriesObserver: (IUserItemView) -> Unit
    private lateinit var followersObserver: (IUserItemView) -> Unit
    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        repositoriesObserver = { userItemView ->
            val user = usersListPresenter.users[userItemView.pos]
            router.navigateTo(screens.userRepositories(user))
        }

        followersObserver = { userItemView ->
            val user = usersListPresenter.users[userItemView.pos]
            router.navigateTo(screens.followers(user))
        }

        usersListPresenter.addRepositoriesObserver(repositoriesObserver)
        usersListPresenter.addFollowersObserver(followersObserver)
    }

    private fun loadData() {
        retrofitGithubUsersRepositoryImpl.getUsers()
            .observeOn(uiScheduler)
            .subscribe({ repos ->
                usersListPresenter.users.addAll(repos)
                viewState.updateList()
            }, {
                Log.e("@@@", "Something went wrong")
            }).disposeBy(compositeDisposable)
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        userScopeContainer.releaseUserScope()
        usersListPresenter.removeRepositoriesObserver(repositoriesObserver)
        usersListPresenter.removeFollowersObserver(followersObserver)
        super.onDestroy()
        compositeDisposable.dispose()

    }

}
