package com.example.financetracker.transactions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.transactions.data.TransactionRepository
import com.example.financetracker.transactions.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TransactionViewModel : ViewModel() {

    private val repo = TransactionRepository()

    // 🔹 Current filter state
    private val _filter = MutableStateFlow(TransactionFilter())

    // 🔹 All transactions from Firestore (real-time)
    private val _transactions = repo.getTransactions()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    // 🔹 FINAL list exposed to UI (filtered + sorted)
    val transactions: StateFlow<List<Transaction>> =
        combine(_transactions, _filter) { list, filter ->
            list
                .filter {
                    (filter.categoryId == null || it.categoryId == filter.categoryId) &&
                            (filter.transactionType == null || it.type == filter.transactionType)
                }
                .sortedWith(
                    when (filter.sortOption) {
                        SortOption.DATE_DESC -> compareByDescending { it.timestamp }
                        SortOption.AMOUNT_DESC -> compareByDescending { it.amount }
                    }
                )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    // 🔹 Update filter (category / sort / type)
    fun updateFilter(filter: TransactionFilter) {
        _filter.value = filter
    }

    // ➕ CREATE
    fun addTransaction(
        amount: Double,
        title: String,
        type: String,
        categoryId: String
    ) {
        repo.addTransaction(
            Transaction(
                amount = amount,
                title = title,
                type = type,
                categoryId = categoryId,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    // ✏️ UPDATE
    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repo.updateTransaction(transaction)
        }
    }

    // 🗑 DELETE
    fun deleteTransaction(id: String) {
        viewModelScope.launch {
            repo.deleteTransaction(id)
        }
    }
}
