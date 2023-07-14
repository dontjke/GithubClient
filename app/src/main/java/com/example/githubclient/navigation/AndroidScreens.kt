package com.example.githubclient.navigation

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepositories
import com.example.githubclient.ui.fragment.FollowersFragment
import com.example.githubclient.ui.fragment.RepositoryFragment
import com.example.githubclient.ui.fragment.UserRepositoriesFragment
import com.example.githubclient.ui.fragment.UsersFragment
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AndroidScreens : IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
    override fun userRepositories(user: GithubUser) =
        FragmentScreen { UserRepositoriesFragment.newInstance(user) }
    override fun followers(user: GithubUser) =
        FragmentScreen { FollowersFragment.newInstance(user) }
    override fun repository(repository: GithubUserRepositories): Screen {
        return FragmentScreen { RepositoryFragment.newInstance(repository) }
    }

}
