package com.example.calculator

import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var solutionTextView: TextView
    private var currentExpression: String = ""
    private var currentResult: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.result_tv)
        solutionTextView = findViewById(R.id.solution_tv)

        // Initialize buttons
        val buttons = listOf<Button>(
            findViewById(R.id.button_0),
            findViewById(R.id.button_1),
            findViewById(R.id.button_2),
            findViewById(R.id.button_3),
            findViewById(R.id.button_4),
            findViewById(R.id.button_5),
            findViewById(R.id.button_6),
            findViewById(R.id.button_7),
            findViewById(R.id.button_8),
            findViewById(R.id.button_9),
            findViewById(R.id.button_plus),
            findViewById(R.id.button_minus),
            findViewById(R.id.button_multiply),
            findViewById(R.id.button_divide),
            findViewById(R.id.button_open_bracket),
            findViewById(R.id.button_close_bracket),
            findViewById(R.id.button_dot),
            findViewById(R.id.button_c),
            findViewById(R.id.button_ac),
            findViewById(R.id.button_equals)
        )

        // Set click listeners for each button
        for (button in buttons) {
            button.setOnClickListener { onButtonClick(button) }
        }
    }

    private fun onButtonClick(button: Button) {
        val buttonText = button.text.toString()
        when {
            buttonText == "C" -> clear()
            buttonText == "AC" -> clearAll()
            buttonText == "=" -> evaluateExpression()
            else -> appendToExpression(buttonText)
        }
    }

    private fun clear() {
        currentExpression = currentExpression.dropLast(1)
        updateTextViews()
    }

    private fun clearAll() {
        currentExpression = ""
        currentResult = ""
        updateTextViews()
    }

    private fun appendToExpression(text: String) {
        currentExpression += text
        updateTextViews()
    }

    private fun evaluateExpression() {
        val webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(this, "Android")
        webView.evaluateJavascript("javascript:eval('$currentExpression')") {
            currentResult = it.replace("\"", "")
            updateTextViews()
        }
    }

    @JavascriptInterface
    fun updateTextViews() {
        resultTextView.text = currentResult
        solutionTextView.text = currentExpression
    }
}