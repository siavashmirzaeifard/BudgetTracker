package com.cyafard.budgettracker.domain.model

import com.cyafard.budgettracker.data.local.entity.TransactionEntity


fun TransactionEntity.toDomain(): Transaction = Transaction(
    id = id,
    title = title,
    amount = amount,
    date = date,
    isIncome = isIncome
)

fun Transaction.toEntity(): TransactionEntity = TransactionEntity(
    id = id,
    title = title,
    amount = amount,
    date = date,
    isIncome = isIncome
)