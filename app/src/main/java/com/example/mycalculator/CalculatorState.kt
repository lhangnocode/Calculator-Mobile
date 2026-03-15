package com.example.mycalculator

data class CalculatorState(
    val num1: String = "",
    val num2: String = "",
    val operation: CalculatorOperation? = null // khi chua nhap phep tinh thi se la null
)