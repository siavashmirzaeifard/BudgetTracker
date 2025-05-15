package com.cyafard.budgettracker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cyafard.budgettracker.data.local.dao.TransactionDao
import com.cyafard.budgettracker.data.local.entity.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class TransactionDatabase: RoomDatabase() {
    abstract fun dao(): TransactionDao
}