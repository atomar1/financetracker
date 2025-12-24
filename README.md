💰 Finance Tracker — Android App

A modern personal finance tracking Android application built using Jetpack Compose, Firebase, and MVVM architecture.
This app allows users to securely track income and expenses, manage categories, and view real-time financial insights with a clean and intuitive UI.

📌 Project Overview

Managing personal finances can be overwhelming.
Finance Tracker helps users:

Track income and expenses

Categorize transactions

View financial summaries

Manage data securely with Firebase

Experience real-time updates and smooth UI interactions

This project was developed as part of a coursework submission and fully satisfies all required criteria.

🎯 Features
🔐 Authentication

Email & Password sign-up and login

Firebase Authentication

Persistent login session

Secure logout

User-specific data isolation

💸 Transactions

Add income and expense transactions

Edit transactions via dialog

Delete transactions with confirmation dialog

Real-time Firestore synchronization

Filter transactions by category

🗂 Categories

Create custom categories

Edit and delete categories

Assign categories to transactions

User-specific categories stored in Firestore

📊 Dashboard

Total Income

Total Expenses

Current Balance

Spending summary by category

Quick navigation actions

🎨 UI & UX

Jetpack Compose with Material 3

Clean, modern layout

Loading, empty, and error states

Confirmation dialogs for destructive actions

Consistent design across screens

🧱 Tech Stack
Frontend

Kotlin

Jetpack Compose

Material Design 3

Navigation Compose

Architecture

MVVM

Repository Pattern

StateFlow

Coroutines

Backend

Firebase Authentication

Firebase Firestore

Real-time listeners for live updates

🏗 App Architecture
com.example.financetracker
│
├── auth/              → Authentication logic & UI
├── dashboard/         → Dashboard UI & ViewModel
├── transactions/      → Transaction CRUD (UI, VM, Repo)
├── categories/        → Category CRUD (UI, VM, Repo)
├── navigation/        → Navigation routes & NavGraph
└── MainActivity.kt

🗃 Data Models
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

📸 Screenshots

📌 Add your screenshots inside a /screenshots folder and update paths below

Screen	Preview
Login

Sign Up

Dashboard

Transactions

Add Transaction

Edit Transaction

Categories

Profile
🎥 Video Walkthrough

📺 YouTube Demo (Unlisted):
👉 [Paste your YouTube link here]

The video demonstrates:

Authentication flow

CRUD operations for transactions & categories

Filtering

Dashboard analytics

Overall app navigation and UX

🚀 Setup Instructions

Clone the repository

Open the project in Android Studio

Add your google-services.json file

Enable in Firebase Console:

Firebase Authentication (Email/Password)

Firebase Firestore

Sync Gradle and run the app

✅ Project Requirements Checklist

✔ Authentication with Firebase
✔ Persistent login
✔ Two Firestore entities (Transactions & Categories)
✔ Full CRUD operations
✔ Real-time data sync
✔ MVVM architecture
✔ Navigation Component
✔ Material 3 UI
✔ Confirmation dialogs
✔ Filter functionality
✔ Clean code & structure

👨‍💻 Author

Ansh
Android Developer
Kotlin | Jetpack Compose | Firebase

📜 License

This project is developed for academic and educational purposes.

⭐ Extra Mile

Dialog-based edit/delete UX

Real-time Firestore flows

Clean separation of concerns

Dashboard analytics logic

Scalable architecture