package com.example.githubclient.di

import com.example.githubclient.di.module.ApiModule
import com.example.githubclient.di.module.AppModule
import com.example.githubclient.di.module.CacheModule
import com.example.githubclient.di.module.CiceroneModule
import com.example.githubclient.di.module.RepositoryModule
import com.example.githubclient.mvp.presenter.ForksPresenter
import com.example.githubclient.mvp.presenter.MainPresenter
import com.example.githubclient.mvp.presenter.UserRepositoriesPresenter
import com.example.githubclient.mvp.presenter.UsersPresenter
import com.example.githubclient.ui.activity.MainActivity
import com.example.githubclient.ui.fragment.UserRepositoriesFragment
import com.example.githubclient.ui.fragment.UsersFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        CacheModule::class,
        CiceroneModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)

    fun inject(usersPresenter: UsersPresenter)
    fun inject(usersFragment: UsersFragment)

    fun inject(userRepositoriesFragment: UserRepositoriesFragment)
    fun inject(userRepositoriesPresenter: UserRepositoriesPresenter)

    fun inject(forksPresenter: ForksPresenter)
}