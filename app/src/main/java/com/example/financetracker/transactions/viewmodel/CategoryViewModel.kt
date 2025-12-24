package com.example.financetracker.transactions.viewmodel

import androidx.lifecycle.ViewModel
import com.example.financetracker.transactions.data.CategoryRepository

class CategoryViewModel : ViewModel() {

    private val repo = CategoryRepository()
    val categories = repo.categories

    init {
        repo.startListening()
    }

    fun addCategory(name: String, color: String) =
        repo.addCategory(name, color)

    fun deleteCategory(id: String) =
        repo.deleteCategory(id)
}
