package com.example.finwise

import androidx.lifecycle.LiveData
import com.example.finwise.database.TransactionDao
import com.example.finwise.database.TransactionEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(private val transactionDao: TransactionDao) {
    suspend fun addTransaction(transactionEntity: TransactionEntity){
        transactionDao.insertTransaction(transactionEntity)
    }

    fun getTransactionsByType(type: String): LiveData<List<TransactionEntity>> {
        return transactionDao.getTransactionsByType(type)

    }
    fun getTotalByType(type: String): LiveData<Double> {
        return transactionDao.getTotalByType(type)
    }
}