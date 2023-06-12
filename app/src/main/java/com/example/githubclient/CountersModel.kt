package com.example.githubclient

class CountersModel {
    private val counters = mutableListOf(0, 0, 0)

    private fun getCurrent(index: Int): Int {
        return counters[index]
    }

    fun next(index: Int): Int {
        counters[index]++
        return getCurrent(index)
    }

    fun set(index: Int, value: Int) {
        counters[index] = value
    }

    companion object {
        const val COUNTER_1 = 0
        const val COUNTER_2 = 1
        const val COUNTER_3 = 2
    }
}