package com.gzozulin.leds

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

private val charPool = listOf('a', 'b', 'c')

data class LedState(val answer: List<Char>, val guess: List<Char>)

class LedViewModel : ViewModel() {
    @VisibleForTesting
    internal var answer = listOf<Char>()

    private val guess = mutableListOf<Char>()

    val state = MutableLiveData<LedState>()

    init {
        reset()
    }

    fun guess(symbol: Char): Boolean {
        check(charPool.contains(symbol))
        if (guess.size == 3) {
            // next attempt
            guess.clear()
        }
        guess.add(symbol)
        updateState()
        return answer == guess
    }

    fun reset() {
        answer = listOf(charPool.random(), charPool.random(), charPool.random())
        guess.clear()
        updateState()
    }

    private fun updateState() {
        state.postValue(LedState(answer, guess))
    }

    companion object {
        fun fromActivity(mainActivity: MainActivity) =
            ViewModelProvider(mainActivity).get(LedViewModel::class.java)
    }
}