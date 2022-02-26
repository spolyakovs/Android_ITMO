package com.example.itmolab3.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentCardsViewModel: ViewModel() {
    private val stream = MutableLiveData<CurrentCardsModel>()

    val modelStream: LiveData<CurrentCardsModel>
        get() = stream

    private val data = testUserlist
    private var currentIndex = 0

    private val topCard
        get() = data[currentIndex % data.size]
    private val bottomCard
        get() = data[(currentIndex + 1) % data.size]

    init {
        updateStream()
    }

    fun swiped() {
        currentIndex += 1
        updateStream()
    }

    private fun updateStream() {
        stream.value = CurrentCardsModel(
            top = topCard,
            bottom = bottomCard
        )
    }
}