package com.example.githubclient

import android.app.Application
import com.example.githubclient.navigation.AndroidScreens
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

class App : Application() {
    companion object {
        lateinit var instance: App
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
    }
}
