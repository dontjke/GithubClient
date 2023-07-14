package com.example.githubclient.mvp.presenter.list

import com.example.githubclient.mvp.view.list.IUserItemView

interface IUserListPresenter : IListPresenter<IUserItemView> {
    fun onRepositoriesClicked(view: IUserItemView)
    fun onFollowersClicked(view: IUserItemView)
}