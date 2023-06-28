package com.example.githubclient.navigation

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import com.github.terrakok.cicerone.Screen

interface IScreens {
    fun users(): Screen
    fun userRepositories(user: GithubUser): Screen
    fun forks(forks: GithubUserRepository): Screen
}