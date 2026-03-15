package com.example.mycalculator

//sealed class dung de phan loai cac trang thai/ hanh dong
//khi khai bao se phai thuc thi het cac truong hop cua no

//Cac class ke thua sealed class se phai khai bao trong cung 1 file
sealed class CalculatorAction {
    data class Number(val number: Int) : CalculatorAction()
    object Clear: CalculatorAction()   //object dai dien cho nhung doi tuong khong mang theo du lieu rieng
    object Delete: CalculatorAction()
    object Decimal: CalculatorAction()
    object Calculate: CalculatorAction()

    object Percentage: CalculatorAction()
    data class Operation(val operation: CalculatorOperation): CalculatorAction()
}