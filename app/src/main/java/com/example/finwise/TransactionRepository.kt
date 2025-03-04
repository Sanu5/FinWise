package com.example.finwise

import androidx.lifecycle.LiveData
import com.example.finwise.database.TransactionDao
import com.example.finwise.database.TransactionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(private val transactionDao: TransactionDao) {
    suspend fun addTransaction(transactionEntity: TransactionEntity){
        transactionDao.insertTransaction(transactionEntity)
    }

    fun getTransactionsByType(type: String): Flow<List<TransactionEntity>> {
        return transactionDao.getTransactionsByType(type)

    }
    fun getTotalByType(type: String): Flow<Double> {
        return transactionDao.getTotalByType(type)
    }
}