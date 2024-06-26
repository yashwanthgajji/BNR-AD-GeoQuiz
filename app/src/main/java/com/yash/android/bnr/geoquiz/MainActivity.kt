package com.yash.android.bnr.geoquiz

import android.app.Activity
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.yash.android.bnr.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()
    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val isCheated = result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
            if (isCheated) {
                quizViewModel.setCurrentQuestionCheated()
                showCheatsRemaining()
            }
        }
    }

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
        binding.cheatButton.setOnClickListener {
            cheatLauncher.launch(CheatActivity.newIntent(this, quizViewModel.currentQuestionAnswer))
        }
        showCheatsRemaining()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            blurCheatButton()
        }
    }

    private fun showCheatsRemaining() {
        val cheatsRemain = "Cheats remaining: ${quizViewModel.cheatsRemaining()}"
        binding.cheatRemainingTextView.text = cheatsRemain
        if (quizViewModel.cheatsRemaining() == 0) {
            binding.cheatButton.isEnabled = false
        }
    }

    private fun showCurrentQuestion() {
        val questionResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionResId)
    }

    private fun checkAnswerAndShowToast(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCurrentQuestionCheated() -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun blurCheatButton() {
        val effect = RenderEffect.createBlurEffect(
            10.0f,
            10.0f,
            Shader.TileMode.CLAMP
        )
        binding.cheatButton.setRenderEffect(effect)
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