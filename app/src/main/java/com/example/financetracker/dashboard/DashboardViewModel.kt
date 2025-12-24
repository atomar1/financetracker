package com.example.financetracker.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.transactions.data.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CategorySpending(
    val categoryId: String,
    val categoryName: String,
    val total: Double
)

class DashboardViewModel : ViewModel() {

    private val repo = TransactionRepository()

    private val _totalIncome = MutableStateFlow(0.0)
    val totalIncome: StateFlow<Double> = _totalIncome

    private val _totalExpense = MutableStateFlow(0.0)
    val totalExpense: StateFlow<Double> = _totalExpense

    private val _balance = MutableStateFlow(0.0)
    val balance: StateFlow<Double> = _balance

    private val _spendingByCategory =
        MutableStateFlow<List<CategorySpending>>(emptyList())
    val spendingByCategory: StateFlow<List<CategorySpending>> =
        _spendingByCategory

    init {
        viewModelScope.launch {
            repo.getTransactions().collect { transactions ->

                val income = transactions
                    .filter { it.type == "INCOME" }
                    .sumOf { it.amount }

                val expense = transactions
                    .filter { it.type == "EXPENSE" }
                    .sumOf { it.amount }

                _totalIncome.value = income
                _totalExpense.value = expense
                _balance.value = income - expense

                // 🔥 GROUP BY CATEGORY
                val grouped = transactions
                    .filter { it.type == "EXPENSE" }
                    .groupBy { it.categoryId to it.title }
                    .map { (key, txs) ->
                        CategorySpending(
                            categoryId = key.first,
                            categoryName = key.second,
                            total = txs.sumOf { it.amount }
                        )
                    }
                    .sortedByDescending { it.total }

                _spendingByCategory.value = grouped
            }
        }
    }
}
