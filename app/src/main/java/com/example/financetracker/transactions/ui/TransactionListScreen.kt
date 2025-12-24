package com.example.financetracker.transactions.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.financetracker.navigation.Routes
import com.example.financetracker.transactions.model.*
import com.example.financetracker.transactions.viewmodel.CategoryViewModel
import com.example.financetracker.transactions.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(navController: NavHostController) {

    val txVM: TransactionViewModel = viewModel()
    val catVM: CategoryViewModel = viewModel()

    val categories by catVM.categories.collectAsState()
    val transactions by txVM.transactions.collectAsState()

    var categoryExpanded by remember { mutableStateOf(false) }
    var sortExpanded by remember { mutableStateOf(false) }

    var filter by remember { mutableStateOf(TransactionFilter()) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.ADD_TRANSACTION) }
            ) {
                Text("+")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            // 🔹 CATEGORY FILTER
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = !categoryExpanded }
            ) {
                OutlinedTextField(
                    value = categories
                        .firstOrNull { it.id == filter.categoryId }
                        ?.name ?: "All Categories",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("All") },
                        onClick = {
                            filter = filter.copy(categoryId = null)
                            txVM.updateFilter(filter)
                            categoryExpanded = false
                        }
                    )

                    categories.forEach {
                        DropdownMenuItem(
                            text = { Text(it.name) },
                            onClick = {
                                filter = filter.copy(categoryId = it.id)
                                txVM.updateFilter(filter)
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // 🔹 SORT DROPDOWN
            ExposedDropdownMenuBox(
                expanded = sortExpanded,
                onExpandedChange = { sortExpanded = !sortExpanded }
            ) {
                OutlinedTextField(
                    value = when (filter.sortOption) {
                        SortOption.DATE_DESC -> "Newest First"
                        SortOption.AMOUNT_DESC -> "Amount High → Low"
                    },
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = sortExpanded,
                    onDismissRequest = { sortExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Newest First") },
                        onClick = {
                            filter = filter.copy(sortOption = SortOption.DATE_DESC)
                            txVM.updateFilter(filter)
                            sortExpanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Amount High → Low") },
                        onClick = {
                            filter = filter.copy(sortOption = SortOption.AMOUNT_DESC)
                            txVM.updateFilter(filter)
                            sortExpanded = false
                        }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // 🔹 LIST
            if (transactions.isEmpty()) {
                Text("No transactions match your filter")
            } else {
                LazyColumn {
                    items(transactions) { tx ->
                        TransactionItem(
                            transaction = tx,
                            onEdit = { txVM.updateTransaction(it) },
                            onDelete = { txVM.deleteTransaction(tx.id) }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionItem(
    transaction: Transaction,
    onEdit: (Transaction) -> Unit,
    onDelete: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    var showEdit by remember { mutableStateOf(false) }
    var showDelete by remember { mutableStateOf(false) }

    ListItem(
        headlineContent = {
            Text(
                if (transaction.type == "EXPENSE")
                    "- $${transaction.amount}"
                else
                    "+ $${transaction.amount}"
            )
        },
        supportingContent = { Text(transaction.title) },
        trailingContent = {
            IconButton(onClick = { showMenu = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = null)
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Edit") },
                    onClick = {
                        showMenu = false
                        showEdit = true
                    }
                )
                DropdownMenuItem(
                    text = { Text("Delete") },
                    onClick = {
                        showMenu = false
                        showDelete = true
                    }
                )
            }
        }
    )

    if (showEdit) {
        EditTransactionDialog(
            transaction = transaction,
            onDismiss = { showEdit = false },
            onSave = {
                onEdit(it)
                showEdit = false
            }
        )
    }

    if (showDelete) {
        AlertDialog(
            onDismissRequest = { showDelete = false },
            title = { Text("Delete Transaction") },
            text = { Text("Are you sure you want to delete this transaction?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    showDelete = false
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDelete = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
