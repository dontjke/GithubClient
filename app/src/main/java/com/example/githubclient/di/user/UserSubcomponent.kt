package com.example.githubclient.di.user

import com.example.githubclient.di.follower.FollowerSubcomponent
import com.example.githubclient.di.repository.RepositorySubcomponent
import com.example.githubclient.di.user.module.UserModule
import com.example.githubclient.mvp.presenter.UsersPresenter
import com.example.githubclient.ui.adapter.UsersRVAdapter
import dagger.Subcomponent

@UserScope
@Subcomponent(
    modules = [
        UserModule::class
    ]
)
interface UserSubcomponent {

    fun repositorySubcomponent(): RepositorySubcomponent

    fun followerSubcomponent(): FollowerSubcomponent

    fun inject(usersPresenter: UsersPresenter)

    fun inject(usersRVAdapter: UsersRVAdapter)
}