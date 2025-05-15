package com.cyafard.budgettracker.presentation.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyafard.budgettracker.domain.model.Transaction
import com.cyafard.budgettracker.domain.usecase.AddTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val addTransaction: AddTransaction
): ViewModel() {
    private val _state = MutableStateFlow(AddUiState(date = today()))
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<AddUiEvent>()
    val event = _event.asSharedFlow()

    fun onTitleChanged(title: String) {
        _state.value = _state.value.copy(title = title)
    }

    fun onAmountChanged(amount: String) {
        _state.value = _state.value.copy(amount = amount)
    }

    fun onDateChanged(date: String) {
        _state.value = _state.value.copy(date = date)
    }

    fun onToggleChanged() {
        _state.value = _state.value.copy(isIncome = !_state.value.isIncome)
    }

    fun onSave() {
        val amount = _state.value.amount.toDoubleOrNull()
        if (_state.value.title.isBlank() || amount == null) {
            viewModelScope.launch(Dispatchers.IO) {
                _event.emit(AddUiEvent.ShowSnackbar("Invalid input"))
            }
            return
        }

        val transaction = Transaction(
            id = 0,
            title = _state.value.title,
            amount = amount,
            date = _state.value.date,
            isIncome = _state.value.isIncome
        )

        viewModelScope.launch(Dispatchers.IO) {
            addTransaction(transaction)
            _event.emit(AddUiEvent.NavigateBack)
        }
    }

    fun onPickDate() {
        viewModelScope.launch(Dispatchers.IO) {
            _event.emit(AddUiEvent.ShowDatePicker)
        }
    }

    private fun today(): String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
}

data class AddUiState(
    val title: String = "",
    val amount: String = "",
    val date: String = "",
    val isIncome: Boolean = true
)

sealed class AddUiEvent {
    object NavigateBack: AddUiEvent()
    object ShowDatePicker: AddUiEvent()
    data class ShowSnackbar(val message: String): AddUiEvent()
}