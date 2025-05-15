package com.cyafard.budgettracker.domain.usecase

import com.cyafard.budgettracker.data.repository.BudgetRepository
import com.cyafard.budgettracker.domain.model.Transaction
import javax.inject.Inject

class DeleteAllTransactions @Inject constructor(private val repository: BudgetRepository) {
    suspend operator fun invoke(transaction: Transaction) =
        repository.deleteTransaction(transaction)
}