package com.example.githubclient.di.repository

import com.example.githubclient.di.repository.module.RepositoryModule
import com.example.githubclient.mvp.presenter.ForksPresenter
import com.example.githubclient.mvp.presenter.UserRepositoriesPresenter
import com.example.githubclient.mvp.presenter.UsersPresenter
import dagger.Subcomponent

@RepositoryScope
@Subcomponent(
    modules = [
        RepositoryModule::class
    ]
)
interface RepositorySubcomponent {

    fun inject(usersPresenter: UsersPresenter)
    fun inject(userRepositoriesPresenter: UserRepositoriesPresenter)
}