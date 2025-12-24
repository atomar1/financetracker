package com.example.financetracker.transactions.model

data class Transaction(
    val id: String = "",
    val amount: Double = 0.0,
    val title: String = "",
    val type: String = "",
    val categoryId: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

