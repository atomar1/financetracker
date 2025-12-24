package com.example.financetracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.financetracker.auth.*
import com.example.financetracker.category.CategoryListScreen
import com.example.financetracker.dashboard.DashboardScreen
import com.example.financetracker.profile.ProfileScreen
import com.example.financetracker.transactions.ui.AddTransactionScreen
import com.example.financetracker.transactions.ui.TransactionListScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()

    val startDestination =
        if (currentUser != null) Routes.DASHBOARD
        else Routes.LOGIN

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(navController, authViewModel)
        }

        composable(Routes.SIGN_UP) {
            SignUpScreen(navController, authViewModel)
        }

        composable(Routes.DASHBOARD) {
            DashboardScreen(navController, authViewModel)
        }

        composable(Routes.TRANSACTIONS) {
            TransactionListScreen(navController)
        }
        composable(Routes.ADD_TRANSACTION) {
            AddTransactionScreen { navController.navigateUp() }
        }
        composable(Routes.CATEGORIES) {
            CategoryListScreen(navController)
        }

        composable(Routes.PROFILE) {
            ProfileScreen(navController, authViewModel)
        }

    }
}
