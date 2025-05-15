package com.cyafard.budgettracker.presentation.screen.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cyafard.budgettracker.domain.model.Transaction

@Composable
fun ExpenseChart(
    modifier: Modifier = Modifier,
    transactions: List<Transaction>
) {
    val income = transactions.filter { it.isIncome }.sumOf { it.amount }
    val outcome = transactions.filter { !it.isIncome }.sumOf { it.amount }

    val total = income + outcome

    val incomeRatio = if (total == 0.0) 0F else (income / total).toFloat()
    val outcomeRatio = if (total == 0.0) 0f else (outcome / total).toFloat()

    Canvas(modifier = modifier.fillMaxWidth().height(150.dp).padding(16.dp)) {
        val width = size.width
        val height = size.height

        drawRect(
            color = Color.Green,
            topLeft = Offset.Zero,
            size = size.copy(width = width * incomeRatio)
        )

        drawRect(
            color = Color.Red,
            topLeft = Offset(x = width * incomeRatio, y = 0f),
            size = size.copy(width = width * outcomeRatio)
        )
    }
}