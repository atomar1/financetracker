💰 Finance Tracker App (Android)

A modern personal finance tracking Android application built using Jetpack Compose, Firebase Firestore, and MVVM architecture.
The app allows users to manage income and expenses, organize spending by categories, and view real-time financial insights.

📱 Features
🔐 Authentication

Firebase Authentication

Secure login & logout

User-specific data isolation

💸 Transactions

Add income and expense transactions

Edit transactions via dialog

Delete transactions with confirmation

Real-time updates from Firestore

🗂 Categories

Create custom expense categories

Color-coded categories

Assign categories to transactions

📊 Dashboard

Total Income

Total Expense

Current Balance

Spending by Category (aggregated & sorted)

Quick navigation actions

🎯 Filtering

Filter transactions by category

View all transactions or category-specific ones

🧱 Tech Stack
Frontend

Kotlin

Jetpack Compose

Material 3

Navigation Compose

Architecture

MVVM (Model–View–ViewModel)

StateFlow

Repository Pattern

Backend

Firebase Authentication

Firebase Firestore

Real-time data streams

🏗 Project Structure
com.example.financetracker
│
├── auth/
│   ├── AuthViewModel.kt
│   └── AuthScreen.kt
│
├── dashboard/
│   ├── DashboardScreen.kt
│   ├── DashboardViewModel.kt
│   └── DashboardComponents.kt
│
├── transactions/
│   ├── model/
│   ├── data/
│   ├── ui/
│   └── viewmodel/
│
├── categories/
│   ├── model/
│   ├── data/
│   ├── ui/
│   └── viewmodel/
│
├── navigation/
│   └── Routes.kt
│
└── MainActivity.kt

🔄 Data Model
Transaction

id

title

amount

type (INCOME / EXPENSE)

categoryId

timestamp

Category

id

name

color

createdAt

🔥 Key Highlights

Fully reactive UI using StateFlow

Firestore real-time listeners

Aggregation logic for dashboard analytics

Clean separation of concerns

Dialog-based edit/delete UX

Material 3 design principles

🚀 Setup Instructions

Clone the repository

Open the project in Android Studio

Add your google-services.json file

Enable:

Firebase Authentication

Firebase Firestore

Run the app on an emulator or physical device

📸 Screens Implemented

Login / Logout

Dashboard

Transaction List

Add Transaction

Edit Transaction (Dialog)

Delete Transaction (Dialog)

Category Management

Profile Screen

📈 Future Improvements

Date range filtering

Charts & visual analytics

Export transactions (CSV)

Monthly summaries

Dark mode enhancements

👨‍💻 Author

Ansh
Android Developer | Kotlin | Jetpack Compose

📜 License

This project is for educational purposes and coursework submission.