package com.cyafard.budgettracker.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cyafard.budgettracker.presentation.screen.home.component.ExpenseChart
import com.cyafard.budgettracker.presentation.screen.home.component.TransactionCard
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest {
            when (it) {
                is HomeUiEvent.ShowSnackbar -> {}
                is HomeUiEvent.NavigateToAdd -> navController.navigate("add")
                is HomeUiEvent.ShareTransaction -> {}
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onEvent(HomeUiEvent.NavigateToAdd()) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add a new transaction"
                )
            }
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is HomeUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is HomeUiState.Error -> {
                    Text((state as HomeUiState.Error).message)
                }

                is HomeUiState.Success -> {
                    val transactions = (state as HomeUiState.Success).transactions

                    Column {
                        ExpenseChart(modifier = modifier, transactions = transactions)
                        LazyColumn {
                            items(transactions) {
                                TransactionCard(
                                    modifier = modifier,
                                    transaction = it,
                                    onEvent = viewModel::onEvent
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}