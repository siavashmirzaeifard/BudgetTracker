package com.cyafard.budgettracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cyafard.budgettracker.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transactionEntity: TransactionEntity)

    @Query("""
        SELECT *
        FROM transactions
        ORDER BY date DESC
    """)
    fun getTransactions(): Flow<List<TransactionEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTransaction(transactionEntity: TransactionEntity)

    @Delete
    suspend fun deleteTransaction(transactionEntity: TransactionEntity)

    @Query("""
        DELETE FROM transactions
    """)
    suspend fun deleteTransactions()
}