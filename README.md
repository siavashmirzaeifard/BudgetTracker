# Budget Tracker Android App

A simple mobile application to track personal income and expenses without a backend. Built with Kotlin and Jetpack Compose, it demonstrates a clean architecture, local persistence with Room, dependency injection with Hilt, and unidirectional data flow using Kotlin Flow.

## Table of Contents
- [Features](#features)
- [Getting Started](#getting-started)
- [Architecture & Design Decisions](#architecture--design-decisions)
- [Technologies Used](#technologies-used)
- [Running the App](#running-the-app)
- [Future Improvements](#future-improvements)

## Features
- **Add Transaction**: Create income or expense entries with title, amount, date, and type.
- **Delete Single**: Long-press on an entry to delete it.
- **Delete All**: Tap the trash icon in the top app bar to remove all entries.
- **Balance Calculation**: Real-time calculation of total balance.
- **Data Persistence**: Transactions are stored locally using Room; data persists across launches.
- **Data Visualization**: A simple chart displays the ratio of income vs. expenses.
- **User Feedback**: Snackbar messages confirm actions (e.g., deletion).
- **Input Validation**: Numeric keyboard for amount input and validation for required fields.

## Getting Started

### Prerequisites
- [Android Studio Arctic Fox (or later)](https://developer.android.com/studio)
- Android SDK (API level 21+)
- Kotlin 1.6+

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/siavashmirzaeifard/BudgetTracker.git
   cd BudgetTracker
   ```
2. **Open in Android Studio**
   - Launch Android Studio
   - Select **Open an existing project**
   - Navigate to the cloned folder
3. **Build & Run**
   - Sync Gradle
   - Press **Run** (▶️) on the emulator or a connected device

## Architecture & Design Decisions

The project follows a clean architecture pattern with the following layers:

- **Data Layer**: 
  - `Room` for local persistence (`TransactionDatabase`, `TransactionDao`).
  - Entities (`TransactionEntity`) and mapping to domain models.
- **Domain Layer**:
  - Plain Kotlin use-case classes (`AddTransaction`, `DeleteTransaction`, `DeleteAllTransactions`, `GetTransactions`, `UpdateTransaction`) for business logic.
- **Presentation Layer**: 
  - Jetpack Compose for UI (`HomeScreen`, `AddScreen`, `SplashScreen`).
  - `ViewModel`s with Hilt injection for state management.
  - Kotlin `Flow` for reactive UI updates.
  - Unidirectional data flow: UI events → `ViewModel` → use-cases → repository → DAO → state updates

Other decisions:
- **Dependency Injection**: Hilt simplifies component wiring.
- **Date Handling**: Uses `DatePickerDialog` and ISO-formatted dates (`yyyy-MM-dd`).
- **Error Handling**: Shows Snackbar for invalid inputs or operations.
- **Charting**: Optional expense/income pie chart using Compose chart library.

## Technologies Used
- **Kotlin**
- **Jetpack Compose**
- **Room**
- **Hilt**
- **Kotlin Coroutines & Flow**
- **Android Navigation Compose**
- **Material3**

## Running the App

1. Follow the [Getting Started](#getting-started) steps.
2. Once running:
   - **Home Screen**: View balance, chart, and transaction list
   - **Add Screen**: Tap ➕ to add a new entry
   - **Delete**: Long-press an item or tap 🗑️ to clear all
