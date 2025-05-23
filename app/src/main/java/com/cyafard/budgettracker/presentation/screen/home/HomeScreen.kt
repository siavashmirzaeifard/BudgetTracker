package com.cyafard.budgettracker.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cyafard.budgettracker.presentation.screen.home.component.ExpenseChart
import com.cyafard.budgettracker.presentation.screen.home.component.TransactionCard
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest {
            when (it) {
                is HomeUiEvent.ShowSnackbar -> { snackbarHostState.showSnackbar(it.message) }
                is HomeUiEvent.NavigateToAdd -> navController.navigate("add")
                is HomeUiEvent.ShareTransaction -> {}
                is HomeUiEvent.DeleteAll -> viewModel.removeAllTransactions()
                is HomeUiEvent.DeleteSingle -> viewModel.removeTransaction(it.transaction)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Budget Tracker") },
                actions = {
                    IconButton(
                        onClick = { viewModel.onEvent(HomeUiEvent.DeleteAll) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete all"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onEvent(HomeUiEvent.NavigateToAdd()) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add a new transaction"
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
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