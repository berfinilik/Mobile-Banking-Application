package com.berfinilik.bankingapplication.Domain

data class Transaction(
    val transactionType: String,
    val amount: Double,
    val date: String,
    val picture:String
)