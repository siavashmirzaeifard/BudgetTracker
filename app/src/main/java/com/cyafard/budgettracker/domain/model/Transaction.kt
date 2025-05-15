package com.cyafard.budgettracker.domain.model

data class Transaction(
    val id: Int,
    val title: String,
    val amount: Double,
    val date: String,
    val isIncome: Boolean
)
