package com.example.financetracker.transactions.data

import com.example.financetracker.transactions.model.Category
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoryRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    fun startListening() {
        firestore.collection("users")
            .document(auth.currentUser!!.uid)
            .collection("categories")
            .addSnapshotListener { snapshot, _ ->
                _categories.value =
                    snapshot?.toObjects(Category::class.java) ?: emptyList()
            }
    }

    fun addCategory(name: String, color: String) {
        val ref = firestore.collection("users")
            .document(auth.currentUser!!.uid)
            .collection("categories")
            .document()

        ref.set(Category(ref.id, name, color))
    }

    fun deleteCategory(id: String) {
        firestore.collection("users")
            .document(auth.currentUser!!.uid)
            .collection("categories")
            .document(id)
            .delete()
    }
}
