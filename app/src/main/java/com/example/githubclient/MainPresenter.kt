package com.example.githubclient

import moxy.MvpPresenter

class MainPresenter(private val model: CountersModel) : MvpPresenter<MainView>() {

    fun incrementCounter1() {
        viewState.setButton1Text(model.next(CountersModel.COUNTER_1).toString())
    }

    fun incrementCounter2() {
        viewState.setButton2Text(model.next(CountersModel.COUNTER_2).toString())
    }

    fun incrementCounter3() {
        viewState.setButton3Text(model.next(CountersModel.COUNTER_3).toString())
    }
}