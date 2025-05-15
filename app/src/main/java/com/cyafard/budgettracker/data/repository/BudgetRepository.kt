package com.cyafard.budgettracker.data.repository

import com.cyafard.budgettracker.data.local.dao.TransactionDao
import com.cyafard.budgettracker.domain.model.Transaction
import com.cyafard.budgettracker.domain.model.toDomain
import com.cyafard.budgettracker.domain.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WalletRepository @Inject constructor(private val dao: TransactionDao) {
    suspend fun addTransaction(transaction: Transaction) =
        dao.insertTransaction(transaction.toEntity())

    fun getTransactions(): Flow<List<Transaction>> =
        dao.getTransactions().map { it.map { it.toDomain() } }

    suspend fun deleteTransaction(transaction: Transaction) =
        dao.deleteTransaction(transaction.toEntity())
}
