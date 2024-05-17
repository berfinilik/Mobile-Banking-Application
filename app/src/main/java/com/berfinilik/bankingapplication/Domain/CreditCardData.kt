package com.berfinilik.bankingapplication.Domain

data class CreditCardData(
    val kartNumarasi: String?,
    val toplamLimit: Double?,
    val kullanilabilirLimit: Double?
)