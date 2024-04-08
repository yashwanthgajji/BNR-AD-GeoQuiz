package com.yash.android.bnr.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.yash.android.bnr.geoquiz.databinding.ActivityMainBinding

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val questionResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionResId)
        binding.trueButton.setOnClickListener { view: View ->
            Snackbar.make(view, R.string.correct_toast, Snackbar.LENGTH_SHORT).show()
        }
        binding.falseButton.setOnClickListener { view: View ->
            Snackbar.make(view, R.string.incorrect_toast, Snackbar.LENGTH_SHORT).show()
        }
    }
}