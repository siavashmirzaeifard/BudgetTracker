package com.cyafard.budgettracker.di

import android.content.Context
import androidx.room.Room
import com.cyafard.budgettracker.data.local.dao.TransactionDao
import com.cyafard.budgettracker.data.local.database.TransactionDatabase
import com.cyafard.budgettracker.data.repository.BudgetRepository
import com.cyafard.budgettracker.domain.usecase.AddTransaction
import com.cyafard.budgettracker.domain.usecase.DeleteAllTransactions
import com.cyafard.budgettracker.domain.usecase.DeleteTransaction
import com.cyafard.budgettracker.domain.usecase.GetTransactions
import com.cyafard.budgettracker.domain.usecase.UpdateTransaction
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTransactionDatabase(@ApplicationContext context: Context): TransactionDatabase =
        Room.databaseBuilder(
            context = context,
            klass = TransactionDatabase::class.java,
            name = "transaction_db"
        )
            .fallbackToDestructiveMigration(dropAllTables = false)
            .build()

    @Provides
    @Singleton
    fun provideDao(db: TransactionDatabase): TransactionDao = db.dao()

    @Provides
    @Singleton
    fun provideRepository(dao: TransactionDao): BudgetRepository =
        BudgetRepository(dao)

    @Provides
    @Singleton
    fun provideUseCases(repository: BudgetRepository) =
        listOf(
            AddTransaction(repository),
            GetTransactions(repository),
            UpdateTransaction(repository),
            DeleteTransaction(repository),
            DeleteAllTransactions(repository)
        )
}