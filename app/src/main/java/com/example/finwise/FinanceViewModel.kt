package com.example.finwise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finwise.database.TransactionEntity
import com.example.finwise.database.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FinanceViewModel @Inject constructor(private val repository: TransactionRepository, private val userRepository: UserRepository) : ViewModel() {

    val incomeTransaction = repository.getTransactionsByType("income")
    val expenseTransaction = repository.getTransactionsByType("expense")

    val totalIncome: LiveData<Double> = repository.getTotalByType("income") ?: MutableLiveData<Double>(0.0)
    val totalExpense: LiveData<Double> = repository.getTotalByType("expense") ?: MutableLiveData<Double>(0.0)


    fun addTransaction(transactionEntity: TransactionEntity) {
        viewModelScope.launch {
            repository.addTransaction(transactionEntity)
        }
    }

    suspend fun getUserByEmail(email: String): User? {
            return userRepository.getUserByEmail(email)

    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }

    }

    fun signUpUser(
        user: User,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Check if the user already exists
                val existingUser = userRepository.getUserByEmail(user.email)
                if (existingUser != null) {
                    withContext(Dispatchers.Main) {
                        onError("User already exists. Please log in.")
                    }
                    return@launch
                }

                // Insert new user
                userRepository.insertUser(user)
                withContext(Dispatchers.Main) {
                    onSuccess()
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Sign-up failed: ${e.localizedMessage}")
                }
            }
        }
    }

    fun loginUser(email: String,
                  onSuccess: () -> Unit,
                  onError: (String) -> Unit){
        viewModelScope.launch {
            try {

            val existingUser = userRepository.getUserByEmail(email)
            if (existingUser != null) {
                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
            catch(e: Exception){
                withContext(Dispatchers.Main) {
                    onError("User doees not exist: ${e.localizedMessage}")
                }
            }
        }

    }
}