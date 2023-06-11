package com.example.githubclient

class MainPresenter(private val view: MainView) {
    private val model = CountersModel()

    fun incrementCounter1() {
        view.setButton1Text(model.next(0).toString())
    }

    fun incrementCounter2() {
        view.setButton2Text(model.next(1).toString())
    }

    fun incrementCounter3() {
        view.setButton3Text(model.next(2).toString())
    }
}