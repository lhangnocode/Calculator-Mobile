package com.example.mycalculator

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

//ViewModel allow to save the state, so that it can not be disappeared
class CalculatorViewModel: ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set //Chi co ban than ViewModel duoc phep thuc hien su thay doi con so

    fun onAction(action: CalculatorAction) {
        when (action) {   // Thong thuong when se di voi else (when == switch case) nhung do la sealed class nen da bao gom tat ca truong hop
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Delete -> performDeletion()
            is CalculatorAction.Percentage -> performPercentage()
        }
    }

    private fun performPercentage() {
        if (state.operation == null) {
            if (state.num1.isNotBlank()) {
                state = state.copy(
                    num1 = (state.num1.toDouble() / 100).toString().take(16)
                )
            }
        }
        else {
            if (state.num2.isNotBlank()) {
                state = state.copy(
                    num2 = (state.num2.toDouble() / 100).toString().take(16)
                )
            }
        }
    }

    private fun performCalculation() {
        val num1 = state.num1.toDoubleOrNull()
        val num2 = state.num2.toDoubleOrNull()
        if (num1 != null && num2 != null) {
            val result = when (state.operation) {
                is CalculatorOperation.Add -> num1 + num2
                is CalculatorOperation.Subtract -> num1 - num2
                is CalculatorOperation.Multiply -> num1 * num2
                is CalculatorOperation.Divide -> num1 / num2
                null -> return
            }
            state = state.copy(
                // toBigDecimal() giúp xử lý số cực lớn một cách chính xác
                // toPlainString() ép nó in ra chuỗi thường, cấm dùng chữ E
                num1 = result.toBigDecimal().toPlainString().take(16),
                num2 = "",
                operation = null
            )
        }
        else {
            println("Invalid input")
            return

        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.num1.isNotBlank()) {  //state la var nen co  the thay doi
            state = state.copy(operation = operation)  //do de mac dinh la gia tri null
        }
    }

    private fun enterDecimal() {
        if (state.operation == null && !state.num1.contains(".") && state.num1.isNotBlank()) {
            state = state.copy(
                num1 = state.num1 + "."
            )
            return
        }
        if (!state.num2.contains(".") && state.num2.isNotBlank()) {
            state = state.copy(
                num2 = state.num2 + "."
            )
            return
        }
    }

    private fun enterNumber(number: Int) {
        if (state.operation == null) {
            if (state.num1.length > MAX_NUM_LENGTH) {
                return
            }
            state = state.copy(
                num1 = state.num1 + number
            )
            return
            }
        if (state.num2.length > MAX_NUM_LENGTH) {
            return
        }
        state = state.copy(
            num2 = state.num2 + number
        )
    }

    companion object {
        private const val MAX_NUM_LENGTH = 12
    }

    private fun performDeletion() {
        when {  // if/else if/else if
            state.num2.isNotBlank() -> state = state.copy(
                num2 = state.num2.dropLast(1)
            )
            state.operation != null -> state = state.copy(
                operation = null
            )
            state.num1.isNotBlank() -> state = state.copy(
                num1 = state.num1.dropLast(1)
            )
        }
    }
}