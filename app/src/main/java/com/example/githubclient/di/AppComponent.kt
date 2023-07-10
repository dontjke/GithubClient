package com.example.githubclient.di

import com.example.githubclient.di.module.ApiModule
import com.example.githubclient.di.module.AppModule
import com.example.githubclient.di.module.CiceroneModule
import com.example.githubclient.di.module.DatabaseModule
import com.example.githubclient.di.module.ImageLoaderModule
import com.example.githubclient.di.user.UserSubcomponent
import com.example.githubclient.mvp.presenter.ForksPresenter
import com.example.githubclient.mvp.presenter.MainPresenter
import com.example.githubclient.mvp.presenter.UserRepositoriesPresenter
import com.example.githubclient.mvp.presenter.UsersPresenter
import com.example.githubclient.ui.activity.MainActivity
import com.example.githubclient.ui.adapter.UsersRVAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        DatabaseModule::class,
        CiceroneModule::class,
        ImageLoaderModule::class,
    ]
)
interface AppComponent {
    fun userSubcomponent(): UserSubcomponent

    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)

    fun inject(forksPresenter: ForksPresenter)
}