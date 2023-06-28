package com.example.githubclient

import android.app.Application
import android.content.Context
import com.bumptech.glide.manager.ConnectivityMonitor
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.network.INetworkStatus
import com.example.githubclient.navigation.AndroidScreens
import com.example.githubclient.ui.network.AndroidNetworkStatus
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

class App : Application() {
    companion object {
        lateinit var instance: App
        lateinit var networkStatus: INetworkStatus
    }

    val androidScreens = AndroidScreens()

    //Временно до даггера положим это тут
    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }
    val navigatorHolder get() = cicerone.getNavigatorHolder()
    val router get() = cicerone.router
    override fun onCreate() {
        super.onCreate()
        instance = this

        networkStatus = AndroidNetworkStatus(instance)
        Database.create(this)
    }
}
