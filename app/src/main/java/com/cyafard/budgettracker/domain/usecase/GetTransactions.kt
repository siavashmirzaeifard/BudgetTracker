package com.cyafard.budgettracker.domain.usecase

import com.cyafard.budgettracker.data.repository.BudgetRepository
import javax.inject.Inject

class GetTransactions @Inject constructor(private val repository: BudgetRepository) {
    operator fun invoke() = repository.getTransactions()
}