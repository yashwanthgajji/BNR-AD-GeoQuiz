package com.yash.android.bnr.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
private const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
private const val ANSWERED_QUESTIONS_KEY = "ANSWERED_QUESTIONS_KEY"
private const val CORRECT_ANSWERS_KEY = "CORRECT_ANSWERS_KEY"
class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    init {
        Log.d(TAG, "ViewModel instance created")
    }

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
    private var answeredQuestions = mutableSetOf<Int>()
    private var correctAnswers = 0

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    val isCurrentQuestionAnswered: Boolean
        get() = answeredQuestions.contains(currentIndex)

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
    }

    fun addCurrentQuestionAsAnswered() {
        answeredQuestions.add(currentIndex)
    }

    fun incrementCorrectAnswer() {
        correctAnswers++
    }

    fun areAllQuestionsAnswered(): Boolean {
        return answeredQuestions.size == questionBank.size
    }

    fun getPercentage(): Int {
        return (correctAnswers * 100) / questionBank.size
    }

    fun clearAnswers() {
        correctAnswers = 0
        answeredQuestions.clear()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
}
