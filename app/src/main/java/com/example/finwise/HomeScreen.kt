package com.example.finwise

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.finwise.database.TransactionEntity

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: FinanceViewModel = hiltViewModel()) {

    val totalIncome = viewModel.totalIncome.collectAsState(0.0).value
    val totalExpense = viewModel.totalExpense.collectAsState(0.0).value
    val incomeTransactions = viewModel.incomeTransaction.collectAsState(emptyList())
    val expenseTransactions = viewModel.expenseTransaction.collectAsState(emptyList())

    val totalBalance = totalIncome - totalExpense
    val spendingPercentage =
        if (totalBalance > 0) (totalExpense / totalBalance * 100).toInt() else 0

    val withFraction by remember { mutableFloatStateOf(((spendingPercentage / 100).toFloat())) }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(colorResource(R.color.main_color))
            .padding(16.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.main_color))
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Hi, Welcome Back",
                    fontSize = 20.sp,
                    color = Color(0xFF052224),
                    fontWeight = FontWeight.Bold
                )

                IconButton(onClick = { TODO() }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notification"
                    )
                }
            }

            Text(
                "Good Morning",
                fontSize = 12.sp,
                color = Color(0xFF052224),
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = colorResource(R.color.main_color),
                border = null
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(
                                "Total Balance", fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = colorResource(R.color.letters)
                            )

                            Text(
                                "$${totalIncome - totalExpense}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                "Total Expense",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal
                            )
                            Text(
                                "-$${totalExpense}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0068FF)
                            )
                        }

                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.90f)
                                    .height(16.dp)
                                    .background(
                                        Color.White,
                                        RoundedCornerShape(4.dp)
                                    )
                                    .padding(end = 4.dp),
                            ) {
                                Text(
                                    text = "$spendingPercentage",
                                    fontSize = 12.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.3f) // 30% progress bar fill
                                        .height(16.dp)
                                        .background(Color(0xFF052224), RoundedCornerShape(4.dp)),
                                    //contentAlignment = Alignment.Start
                                )

                                Text(
                                    text = "$20,000.00",
                                    fontSize = 12.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.End
                                )

                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))


                    }
                }

            }
        }

        Box(
            modifier = Modifier.fillMaxHeight()
                .background(
                    color = colorResource(R.color.light_green),
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
        ) {

            Column(
                modifier = Modifier.fillMaxHeight()
            ) {

            }
        }

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
                            Text(
                                transaction.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
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
