package com.example.finwise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finwise.database.TransactionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinanceViewModel @Inject constructor(private val repository: TransactionRepository) : ViewModel() {

    val incomeTransaction = repository.getTransactionsByType("income")
    val expenseTransaction = repository.getTransactionsByType("expense")

    val totalIncome = repository.getTotalByType("income")
    val totalExpense = repository.getTotalByType("expense")

    fun addTransaction(transactionEntity: TransactionEntity) {
        viewModelScope.launch {
            repository.addTransaction(transactionEntity)
        }
    }
}