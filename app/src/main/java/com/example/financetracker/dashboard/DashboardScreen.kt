package com.example.financetracker.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.financetracker.auth.AuthViewModel
import com.example.financetracker.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val dashboardVM: DashboardViewModel = viewModel()

    val income by dashboardVM.totalIncome.collectAsState()
    val expense by dashboardVM.totalExpense.collectAsState()
    val balance by dashboardVM.balance.collectAsState()

    val spending by dashboardVM.spendingByCategory.collectAsState()

    Text(
        text = "Spending by Category",
        style = MaterialTheme.typography.titleMedium
    )

    if (spending.isEmpty()) {
        Text(
            text = "No expenses yet",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            spending.forEach {
                CategorySpendingItem(
                    name = it.categoryName,
                    amount = it.total
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Routes.PROFILE)
                    }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // 🔢 SUMMARY CARDS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                SummaryCard(
                    title = "Income",
                    amount = income,
                    color = MaterialTheme.colorScheme.primary
                )

                SummaryCard(
                    title = "Expense",
                    amount = expense,
                    color = MaterialTheme.colorScheme.error
                )
            }

            SummaryCard(
                title = "Balance",
                amount = balance,
                color = if (balance >= 0)
                    MaterialTheme.colorScheme.tertiary
                else
                    MaterialTheme.colorScheme.error
            )

            Spacer(Modifier.height(8.dp))

            Text("Quick Actions", style = MaterialTheme.typography.titleMedium)

            ActionCard(
                icon = Icons.Default.List,
                label = "View Transactions"
            ) {
                navController.navigate(Routes.TRANSACTIONS)
            }

            ActionCard(
                icon = Icons.Default.Add,
                label = "Add Transaction"
            ) {
                navController.navigate(Routes.ADD_TRANSACTION)
            }

            ActionCard(
                icon = Icons.Default.Category,
                label = "Manage Categories"
            ) {
                navController.navigate(Routes.CATEGORIES)
            }

            ActionCard(
                icon = Icons.Default.Logout,
                label = "Logout",
                isDanger = true
            ) {
                authViewModel.logout()
                navController.navigate(Routes.LOGIN) {
                    popUpTo(0)
                }
            }
        }
    }
}
