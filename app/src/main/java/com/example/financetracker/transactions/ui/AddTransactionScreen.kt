package com.example.financetracker.transactions.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financetracker.transactions.viewmodel.CategoryViewModel
import com.example.financetracker.transactions.viewmodel.TransactionViewModel
import androidx.compose.material3.ExposedDropdownMenuDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    onTransactionAdded: () -> Unit
) {
    val txVM: TransactionViewModel = viewModel()
    val catVM: CategoryViewModel = viewModel()

    val categories by catVM.categories.collectAsState()

    var amount by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("EXPENSE") }
    var selectedCategoryId by remember { mutableStateOf("") }
    var selectedCategoryName by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedCategoryName,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach {
                    DropdownMenuItem(
                        text = { Text(it.name) },
                        onClick = {
                            selectedCategoryId = it.id
                            selectedCategoryName = it.name
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Row {
            FilterChip(
                selected = type == "EXPENSE",
                onClick = { type = "EXPENSE" },
                label = { Text("Expense") }
            )
            Spacer(Modifier.width(8.dp))
            FilterChip(
                selected = type == "INCOME",
                onClick = { type = "INCOME" },
                label = { Text("Income") }
            )
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                val parsed = amount.toDoubleOrNull()
                if (parsed != null && selectedCategoryId.isNotEmpty()) {
                    txVM.addTransaction(
                        amount = parsed,
                        title = title,
                        type = type,
                        categoryId = selectedCategoryId
                    )
                    onTransactionAdded()
                }
            },
            enabled = amount.isNotBlank() && selectedCategoryId.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Transaction")
        }
    }
}
