package com.example.finwise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.finwise.database.TransactionEntity

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val viewModel : FinanceViewModel = hiltViewModel()
    val totalIncome = viewModel.totalIncome.observeAsState(0.0).value ?: 0.0
    val totalExpense = viewModel.totalExpense.observeAsState(0.0).value ?: 0.0
    val incomeTransactions = viewModel.incomeTransaction.observeAsState(emptyList())
    val expenseTransactions = viewModel.expenseTransaction.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB2F2BB))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hi, Welcome Back", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Good Morning", fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Total Balance", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                Text("$${totalIncome - totalExpense}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Blue)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Total Expense", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                Text("-$${totalExpense}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Red)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Transactions", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        TransactionList(expenseTransactions.value + incomeTransactions.value)
    }
}

@Composable
fun TransactionList(transactions: List<TransactionEntity>) {
    Column {
        transactions.forEach { transaction ->
            Card(
                shape = RoundedCornerShape(12.dp),
                backgroundColor = Color.White,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(transaction.title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        Text(transaction.category, fontSize = 14.sp, color = Color.Gray)
                    }
                    Text(
                        text = if (transaction.type == "income") "+$${transaction.amount}" else "-$${transaction.amount}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (transaction.type == "income") Color.Green else Color.Red
                    )
                }
            }
        }
    }
}