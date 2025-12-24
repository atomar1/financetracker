package com.example.financetracker.transactions.model

data class TransactionFilter(
    val categoryId: String? = null,
    val transactionType: String? = null, // "INCOME" or "EXPENSE"
    val sortOption: SortOption = SortOption.DATE_DESC
)

enum class SortOption {
    DATE_DESC,
    AMOUNT_DESC
}
