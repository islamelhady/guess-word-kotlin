package com.elhady.gesswordkotlin.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.elhady.gesswordkotlin.model.MAX_NO_OF_WORDS
import com.elhady.gesswordkotlin.model.SCORE_INCREASE
import com.elhady.gesswordkotlin.model.allWordsList

/**
 * Created by islam elhady on 24-Jan-21.
 */
class GameViewModel : ViewModel() {

    private var _score = 0
    val score: Int
        get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount

    private lateinit var _currentGuessWord: String
    val currentGuessWord: String
        get() = _currentGuessWord


    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    init {
        Log.d("GameFragment", "GameViewModel created")
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed")
    }

    /*
    * Updates currentWord and currentScrambledWord with the next word.
    */
    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while (tempWord.toString().equals(currentWord, false)) {
            tempWord.shuffle()
        }

        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentGuessWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    /*
    * Re-initializes the game data to restart the game.
    */
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }

    /*
    * Increases the game score if the player's word is correct.
    */
    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    /*
    * Returns true if the player word is correct.
    * Increases the score accordingly.
    */
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    /*
    * Returns true if the current word count is less than MAX_NO_OF_WORDS
    */
    fun nextWord(): Boolean {
        return if (_currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }
}