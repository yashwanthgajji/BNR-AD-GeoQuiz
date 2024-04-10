package com.yash.android.bnr.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.yash.android.bnr.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0
    private var answeredQuestions = mutableSetOf<Int>()
    private var correctAnswers = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showCurrentQuestion()
        binding.trueButton.setOnClickListener {
            checkAnswerAndShowToast(true)
        }
        binding.falseButton.setOnClickListener {
            checkAnswerAndShowToast(false)
        }
        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            showCurrentQuestion()
        }
        binding.prevButton.setOnClickListener {
            currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
            showCurrentQuestion()
        }
        binding.questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            showCurrentQuestion()
        }
    }

    private fun showCurrentQuestion() {
        val questionResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionResId)
        if (!answeredQuestions.contains(currentIndex)) {
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        } else {
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        }
    }

    private fun checkAnswerAndShowToast(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            correctAnswers++
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        answeredQuestions.add(currentIndex)
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        if (answeredQuestions.size == questionBank.size) {
            val percentage = (correctAnswers * 100) / questionBank.size
            Toast.makeText(this, "Your percentage is $percentage", Toast.LENGTH_SHORT).show()
            correctAnswers = 0
            answeredQuestions.clear()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}