package com.example.githubclient.mvp.view.list

interface IFollowerItemView : IItemView {
    fun setLogin(text: String)
    fun loadAvatar(url: String)
}