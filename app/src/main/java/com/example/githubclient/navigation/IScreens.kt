package com.example.githubclient.navigation

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepositories
import com.github.terrakok.cicerone.Screen

interface IScreens {
    fun users(): Screen
    fun userRepositories(user: GithubUser): Screen
    fun forks(forks: GithubUserRepositories): Screen
}