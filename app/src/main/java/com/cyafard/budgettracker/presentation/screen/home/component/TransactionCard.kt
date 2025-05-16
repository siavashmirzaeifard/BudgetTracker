package com.cyafard.budgettracker.presentation.screen.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cyafard.budgettracker.domain.model.Transaction
import com.cyafard.budgettracker.presentation.screen.home.HomeUiEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionCard(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    onEvent: (HomeUiEvent) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(8.dp)
            .combinedClickable(
                onClick = {},
                onLongClick = {
                    onEvent(HomeUiEvent.DeleteSingle(transaction))
                }
            )
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(text = transaction.title, style = MaterialTheme.typography.titleMedium)
            Text(text = transaction.date)
            Text(
                text = (if (transaction.isIncome) "+" else "-") + "$${transaction.amount}",
                color = if (transaction.isIncome) Color.Green else Color.Red
            )
        }
    }
}