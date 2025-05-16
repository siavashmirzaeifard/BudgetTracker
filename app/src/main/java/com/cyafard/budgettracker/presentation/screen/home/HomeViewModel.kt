package com.cyafard.budgettracker.presentation.screen.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyafard.budgettracker.domain.model.Transaction
import com.cyafard.budgettracker.domain.usecase.DeleteAllTransactions
import com.cyafard.budgettracker.domain.usecase.DeleteTransaction
import com.cyafard.budgettracker.domain.usecase.GetTransactions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTransactions: GetTransactions,
    private val deleteTransaction: DeleteTransaction,
    private val deleteAllTransactions: DeleteAllTransactions

): ViewModel() {
    private val _uiState =
        MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HomeUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        getTransactions()
            .onEach { _uiState.value = HomeUiState.Success(it) }
            .catch { _uiState.value = HomeUiState.Error(it.localizedMessage ?: "Unknown Error") }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: HomeUiEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiEvent.emit(event)
        }
    }

    fun removeTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTransaction(transaction)
            _uiEvent.emit(HomeUiEvent.ShowSnackbar("Transaction deleted"))
        }
    }

    fun removeAllTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllTransactions()
            _uiEvent.emit(HomeUiEvent.ShowSnackbar("All transactions deleted"))
        }
    }
}

sealed class HomeUiState {
    object Loading: HomeUiState()
    data class Success(val transactions: List<Transaction>): HomeUiState()
    data class Error(val message: String): HomeUiState()
}

sealed class HomeUiEvent {
    data class NavigateToAdd(val id: Int? = null): HomeUiEvent()
    data class ShowSnackbar(val message: String): HomeUiEvent()
    data class ShareTransaction(val amount: Double): HomeUiEvent()
    data class DeleteSingle(val transaction: Transaction): HomeUiEvent()
    object DeleteAll: HomeUiEvent()
}