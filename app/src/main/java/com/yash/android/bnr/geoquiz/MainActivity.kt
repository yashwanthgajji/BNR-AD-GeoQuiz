package com.yash.android.bnr.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.yash.android.bnr.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a ViewModel: $quizViewModel")

        showCurrentQuestion()
        binding.trueButton.setOnClickListener {
            checkAnswerAndShowToast(true)
        }
        binding.falseButton.setOnClickListener {
            checkAnswerAndShowToast(false)
        }
        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            showCurrentQuestion()
        }
        binding.prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            showCurrentQuestion()
        }
        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            showCurrentQuestion()
        }
    }

    private fun showCurrentQuestion() {
        val questionResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionResId)
    }

    private fun checkAnswerAndShowToast(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
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