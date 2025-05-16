package com.cyafard.budgettracker.domain.usecase

import com.cyafard.budgettracker.data.repository.BudgetRepository
import javax.inject.Inject

class DeleteAllTransactions @Inject constructor(private val repository: BudgetRepository) {
    suspend operator fun invoke() =
        repository.deleteAllTransactions()
}