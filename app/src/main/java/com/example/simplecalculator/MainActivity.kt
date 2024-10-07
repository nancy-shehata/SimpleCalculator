package com.example.simplecalculator

import android.os.Bundle

import android.widget.Button

import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

import java.lang.String.format



class MainActivity : AppCompatActivity() {



    private lateinit var resultTextView: TextView

    private var operand1: Double = 0.0

    private var operand2: Double = 0.0

    private var pendingOperation = ""

    private var userIsInMiddleOfTyping = false



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)



        resultTextView = findViewById(R.id.tvResult)



        val button0: Button = findViewById(R.id.btn0)

        val button1: Button = findViewById(R.id.btn1)

        val button2: Button = findViewById(R.id.btn2)

        val button3: Button = findViewById(R.id.btn3)

        val button4: Button = findViewById(R.id.btn4)

        val button5: Button = findViewById(R.id.btn5)

        val button6: Button = findViewById(R.id.btn6)

        val button7: Button = findViewById(R.id.btn7)

        val button8: Button = findViewById(R.id.btn8)

        val button9: Button = findViewById(R.id.btn9)

        val buttonDecimal: Button = findViewById(R.id.btnDecimal)

        val buttonAdd: Button = findViewById(R.id.btnAdd)

        val buttonSubtract: Button = findViewById(R.id.btnSubtract)

        val buttonMultiply: Button = findViewById(R.id.btnMultiply)

        val buttonDivide: Button = findViewById(R.id.btnDivide)

        val buttonClear: Button = findViewById(R.id.btnClear)

        val buttonEquals: Button = findViewById(R.id.btnEqual)



        val numberButtons = listOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9)



        for (button in numberButtons) {

            button.setOnClickListener { numberPressed(button.text.toString())
            }

        }



        buttonDecimal.setOnClickListener { decimalPressed() }

        buttonAdd.setOnClickListener { operationPressed("+") }

        buttonSubtract.setOnClickListener { operationPressed("-") }

        buttonMultiply.setOnClickListener { operationPressed("*") }

        buttonDivide.setOnClickListener { operationPressed("/") }



        buttonEquals.setOnClickListener { equalsPressed() }

        buttonClear.setOnClickListener { clearPressed() }



        // Restore the state if applicable

        if (savedInstanceState != null) {

            resultTextView.text = savedInstanceState.getString("resultText", "0")

            operand1 = savedInstanceState.getDouble("operand1")

            operand2 = savedInstanceState.getDouble("operand2")

            pendingOperation = savedInstanceState.getString("pendingOperation", "")

            userIsInMiddleOfTyping = savedInstanceState.getBoolean("userIsInMiddleOfTyping")

        }

    }



    override fun onSaveInstanceState(outState: Bundle) {

        super.onSaveInstanceState(outState)

        outState.putString("resultText", resultTextView.text.toString())

        outState.putDouble("operand1", operand1)

        outState.putDouble("operand2", operand2)

        outState.putString("pendingOperation", pendingOperation)

        outState.putBoolean("userIsInMiddleOfTyping", userIsInMiddleOfTyping)

    }



    private fun numberPressed(number: String) {

        if (userIsInMiddleOfTyping) {

            resultTextView.text = resultTextView.text.toString() + number

        } else {

            resultTextView.text = number

            userIsInMiddleOfTyping = true

        }

    }



    private fun decimalPressed() {

        if (!userIsInMiddleOfTyping) {

            resultTextView.text = "0."

            userIsInMiddleOfTyping = true

        } else if (!resultTextView.text.contains(".")) {

            resultTextView.text = resultTextView.text.toString() + "."

        }

    }



    private fun operationPressed(operation: String) {

        operand1 = resultTextView.text.toString().toDoubleOrNull() ?: 0.0

        pendingOperation = operation

        userIsInMiddleOfTyping = false

    }



    private fun equalsPressed() {

        operand2 = resultTextView.text.toString().toDoubleOrNull() ?: 0.0

        val result = when (pendingOperation) {

            "+" -> operand1 + operand2

            "-" -> operand1 - operand2

            "*" -> operand1 * operand2

            "/" -> if (operand2 != 0.0) operand1 / operand2 else "Error"

            else -> 0.0

        }

        resultTextView.text = if (result is String) result else format("%.3f", result)

        userIsInMiddleOfTyping = false

    }



    private fun clearPressed() {

        resultTextView.text = "0"

        operand1 = 0.0

        operand2 = 0.0

        pendingOperation = ""

        userIsInMiddleOfTyping = false

    }

}