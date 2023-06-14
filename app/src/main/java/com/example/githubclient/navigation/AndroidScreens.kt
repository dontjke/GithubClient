package com.example.githubclient.navigation

import com.example.githubclient.ui.fragment.UserFragment
import com.example.githubclient.ui.fragment.UsersFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AndroidScreens : IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
    override fun user(id: Int) = FragmentScreen { UserFragment.newInstance(id) }
}
