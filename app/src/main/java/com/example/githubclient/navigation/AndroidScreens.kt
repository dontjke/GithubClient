package com.example.githubclient.navigation

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepositories
import com.example.githubclient.ui.fragment.ForksFragment
import com.example.githubclient.ui.fragment.UserRepositoriesFragment
import com.example.githubclient.ui.fragment.UsersFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AndroidScreens : IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
    override fun userRepositories(user: GithubUser) =
        FragmentScreen { UserRepositoriesFragment.newInstance(user) }

    override fun forks(forks: GithubUserRepositories) =
        FragmentScreen { ForksFragment.newInstance(forks) }
}