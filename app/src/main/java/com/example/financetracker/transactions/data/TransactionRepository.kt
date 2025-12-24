package com.example.financetracker.transactions.data

import com.example.financetracker.transactions.model.Transaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class TransactionRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun collection() =
        firestore.collection("users")
            .document(auth.currentUser!!.uid)
            .collection("transactions")

    fun getTransactions(): Flow<List<Transaction>> = callbackFlow {
        val listener = collection()
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, _ ->
                val list = snapshot?.toObjects(Transaction::class.java) ?: emptyList()
                trySend(list)
            }
        awaitClose { listener.remove() }
    }

    fun getTransactionsByCategory(categoryId: String): Flow<List<Transaction>> =
        callbackFlow {
            val listener = collection()
                .whereEqualTo("categoryId", categoryId)
                .addSnapshotListener { snapshot, _ ->
                    val list = snapshot?.toObjects(Transaction::class.java) ?: emptyList()
                    trySend(list)
                }
            awaitClose { listener.remove() }
        }

    fun addTransaction(transaction: Transaction) {
        val doc = collection().document()
        doc.set(transaction.copy(id = doc.id))
    }

    // ✏️ UPDATE
    fun updateTransaction(transaction: Transaction) {
        collection()
            .document(transaction.id)
            .set(transaction)
    }

    // 🗑 DELETE
    fun deleteTransaction(transactionId: String) {
        collection()
            .document(transactionId)
            .delete()
    }
}
