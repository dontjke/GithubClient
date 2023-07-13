package com.example.githubclient.mvp.presenter

import android.widget.ImageView
import com.example.githubclient.di.follower.module.IFollowerScopeContainer
import com.example.githubclient.mvp.model.cache.repo.IGithubFollowersRepository
import com.example.githubclient.mvp.model.entity.GithubFollower
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.presenter.list.IFollowersListPresenter
import com.example.githubclient.mvp.view.FollowersView
import com.example.githubclient.mvp.view.IImageLoader
import com.example.githubclient.mvp.view.list.IFollowerItemView
import com.example.githubclient.navigation.IScreens
import com.example.githubclient.utils.disposeBy
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import javax.inject.Inject

class FollowersPresenter(private val user: GithubUser?) : MvpPresenter<FollowersView>() {
    @Inject
    lateinit var dataBase: Database

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

    @Inject
    lateinit var followersRepository: IGithubFollowersRepository

    @Inject
    lateinit var uiScheduler: Scheduler

    @Inject
    lateinit var followersScopeContainer: IFollowerScopeContainer
    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    private var compositeDisposable = CompositeDisposable()

    class FollowersListPresenter : IFollowersListPresenter {
        val followers = mutableListOf<GithubFollower>()

        override var itemClickListener: ((IFollowerItemView) -> Unit)? = null
        override fun bindView(view: IFollowerItemView) {
            val follower = followers[view.pos]
            follower.login.let {
                view.setLogin(it)
            }
            follower.avatarUrl?.let {
                view.loadAvatar(it)
            }
        }

        override fun getCount(): Int = followers.size
    }

    val followersListPresenter = FollowersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
    }

    private fun loadData() {
        user?.let {
            followersRepository.getFollowers(it)
                .observeOn(uiScheduler)
                .subscribe({ followers ->
                    followersListPresenter.followers.clear()
                    followersListPresenter.followers.addAll(followers)
                    viewState.updateList()
                }, {
                    println("Error: ${it.message}")
                }).disposeBy(compositeDisposable)
        }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        followersScopeContainer.releaseFollowerScope()
        super.onDestroy()
        compositeDisposable.dispose()
    }
}