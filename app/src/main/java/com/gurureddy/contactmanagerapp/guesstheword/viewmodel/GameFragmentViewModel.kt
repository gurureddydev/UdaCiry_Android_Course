package com.gurureddy.contactmanagerapp.guesstheword.viewmodel

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.view.SurfaceControl.Transaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameFragmentViewModel : ViewModel() {
    companion object {
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIME = 10000L

        private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
        private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
        private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
        private val NO_BUZZ_PATTERN = longArrayOf(0)

    }

    private val timer: CountDownTimer
    private lateinit var wordList: MutableList<String>
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }
    private val _eventBuzz = MutableLiveData<BuzzType>()
    val eventBuzz: LiveData<BuzzType>
        get() = _eventBuzz

    init {
        resetList()
        nextWord()
        _score.value = 0
        _eventGameFinish.value = false

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millsUnitlFinished: Long) {
                _currentTime.value = (millsUnitlFinished / ONE_SECOND)
            }

            override fun onFinish() {
                _eventGameFinish.value = true
                resetList()
                nextWord()
            }
        }
        timer.start()
    }

    private fun resetList() {
        wordList = mutableListOf(
            "King",
            "Power",
            "Knowledge",
            "Money",
            "Quality",
            "Life",
            "Strength",
            "Mountain",
            "Rock",
            "Hungry",
            "Book",
            "Coding",
            "Cocking",
            "Love",
            "Happy",
            "Time",
            "Car",
            "Chains",
            "Gold",
            "muscles"
        )
        wordList.shuffle()
    }

    private fun nextWord() {
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

    fun onSkip() {
        _score.value = score.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = score.value?.plus(1)
        nextWord()
    }

    fun eventGameFinished() {
        _eventGameFinish.value = false
    }

    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    fun onBuzzComplete() {
        _eventBuzz.value = BuzzType.NO_BUZZ
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}
