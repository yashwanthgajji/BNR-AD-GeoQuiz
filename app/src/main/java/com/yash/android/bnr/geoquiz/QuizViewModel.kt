package com.yash.android.bnr.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val CURR_QUESTION_CHEAT_KEY = "CURR_QUESTION_CHEAT_KEY"
const val NUM_CHEATED_KEY = "NUM_CHEATED_KEY"
class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex: Int
        get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)
    private var questionsCheated: BooleanArray
        get() = savedStateHandle[CURR_QUESTION_CHEAT_KEY] ?: BooleanArray(questionBank.size)
        set(value) = savedStateHandle.set(CURR_QUESTION_CHEAT_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    private val MAX_CHEATS = 3
    private var numTimesCheated: Int
        get() = savedStateHandle[NUM_CHEATED_KEY] ?: 0
        set(value) = savedStateHandle.set(NUM_CHEATED_KEY, value)

    fun cheatsRemaining() = MAX_CHEATS - numTimesCheated

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
    }

    fun isCurrentQuestionCheated() : Boolean {
        return questionsCheated[currentIndex]
    }

    fun setCurrentQuestionCheated() {
        val tempArray = questionsCheated
        tempArray[currentIndex] = true
        questionsCheated = tempArray
        numTimesCheated++
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
}
