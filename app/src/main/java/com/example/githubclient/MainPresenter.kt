package com.example.githubclient

class MainPresenter(private val view: MainView) {
    private val model = CountersModel()

    fun counterClick(id: Int) {
        when (id) {
            R.id.counterButton1 -> {
                val nextValue = model.next(0)
                view.setButtonText(0, nextValue.toString())
            }
            R.id.counterButton2 -> {
                val nextValue = model.next(1)
                view.setButtonText(1, nextValue.toString())
            }
            R.id.counterButton3 -> {
                val nextValue = model.next(2)
                view.setButtonText(2, nextValue.toString())
            }
        }
    }
}