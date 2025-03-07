package com.example.finwise

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.finwise.database.TransactionEntity
import java.nio.file.WatchEvent
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.Clock.offset
import java.util.Date
import java.util.Locale


@Composable
fun HomeScreen(
   navHostController: NavHostController,
    viewModel: FinanceViewModel = hiltViewModel()
) {
    val totalIncome = viewModel.totalIncome.collectAsState(0.0).value
    val totalExpense = viewModel.totalExpense.collectAsState(0.0).value
    val incomeTransactions = viewModel.incomeTransaction.collectAsState(emptyList())
    val expenseTransactions = viewModel.expenseTransaction.collectAsState(emptyList())

    val totalBalance = totalIncome - totalExpense
    val spendingPercentage = calculateSpendingPercentage(totalIncome, totalExpense)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.main_color))
            .padding(16.dp)
        ) {
            HeaderSection()
            Spacer(modifier = Modifier.height(16.dp))
            BalanceCard(totalBalance, totalExpense, spendingPercentage)
            Spacer(modifier = Modifier.height(24.dp))
            TransactionSection(incomeTransactions.value + expenseTransactions.value) // Combined list
        }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        BottomNavBar(modifier = Modifier, navHostController)
    }
}


@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 16.dp, start = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                "Hi, Welcome Back",
                fontSize = 20.sp,
                color = Color(0xFF052224),
                fontWeight = FontWeight.Bold
            )
            Text(
                "Good Morning",
                fontSize = 12.sp,
                color = Color(0xFF052224),
                fontWeight = FontWeight.Normal
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notification",
                tint = Color.White
            )
        }
    }
}

@Composable
fun BalanceCard(totalBalance: Double, totalExpense: Double, spendingPercentage: Int) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = colorResource(R.color.main_color)

       // border = BorderStroke(1.dp, colorResource(R.color.main_color)), // Optional border
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        "Total Balance",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(R.color.letters)
                    )
                    Text(
                        formatCurrency(totalBalance),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Canvas(
                    modifier = Modifier.height(40.dp).width(1.dp)
                ) {
                    drawLine(
                        color = Color.White,
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = 0f, y = size.height),
                        strokeWidth = 2f // Adjust line thickness if needed
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "Total Expense",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(R.color.letters)
                    )
                    Text(
                        "-${formatCurrency(totalExpense)}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0068FF)
                    )
                }
            }

            ProgressBar(percentage = spendingPercentage)

            Text(
                text = "${spendingPercentage}% of Your Expenses, Looks Good.",
                fontSize = 12.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ProgressBar(percentage: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.90f)
                    .height(20.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(8.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(percentage / 100f)
                        .height(20.dp)
                        .background(Color(0xFF052224),
                            RoundedCornerShape(8.dp))
                )
            }
        }
    }
}


@Composable
fun TransactionSection(transactions: List<TransactionEntity>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(R.color.light_green),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "Transactions",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        if (transactions.isEmpty()) {
            Text("No transactions yet.")
        } else {
            TransactionList(transactions = transactions)
        }
    }
}
@Composable
fun TransactionList(transactions: List<TransactionEntity>) {
    LazyColumn {
        items(transactions) { transaction ->
            TransactionItem(transaction = transaction)
        }
    }
}

@Composable
fun TransactionItem(transaction: TransactionEntity) {
    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color.White,
        modifier = Modifier.padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    transaction.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    formatDate(transaction.date),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Text(
                formatCurrency(transaction.amount),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = if (transaction.type == "income") Color.Green else Color.Red
            )
        }
    }
}

// Utility functions
fun calculateSpendingPercentage(income: Double, expense: Double): Int {
    return if (income > 0) (expense / income * 100).toInt().coerceIn(0, 100) else 0
}

fun formatCurrency(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale.US)
    return format.format(amount)
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val date = Date(timestamp)
    return sdf.format(date)
}

@Composable
fun BottomNavBar(modifier: Modifier = Modifier, navHostController: NavHostController){
    modifier.height(32.dp).clip(RoundedCornerShape(16.dp))
    var selectedItem by remember { mutableIntStateOf(0) }

    val items = listOf(
        Pair("Home", R.drawable.ic_home),
        Pair("Analysis", R.drawable.ic_analysis),
        Pair("Transaction", R.drawable.ic_transaction),
        Pair("category", R.drawable.ic_category),
        Pair("Profile", R.drawable.ic_profile),
    )

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFDFF7E2))
            .clip(RoundedCornerShape(16.dp)),
        backgroundColor = Color(0xFFDFF7E2),
        elevation = 8.dp
    ) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                    Box(
                        modifier = if(index == selectedItem){
                            Modifier
                                .clip(CircleShape)
                                .background(colorResource(R.color.main_color))
                                .padding(8.dp)
                        } else Modifier
                    ){
                        Icon(
                            painter = painterResource(id = item.second),
                            contentDescription = item.first,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    if (index == 0) navHostController.navigate("home")
                    else if(index == 1) navHostController.navigate("analysis")

                }
            )
        }
    }
}


//@Composable
//fun HomeScreen(
//    navHostController: NavHostController,
//    viewModel: FinanceViewModel = hiltViewModel()) {
//
//    val totalIncome = viewModel.totalIncome.collectAsState(0.0).value
//    val totalExpense = viewModel.totalExpense.collectAsState(0.0).value
//    val incomeTransactions = viewModel.incomeTransaction.collectAsState(emptyList())
//    val expenseTransactions = viewModel.expenseTransaction.collectAsState(emptyList())
//
//
//    val totalBalance = totalIncome - totalExpense
//    val spendingPercentage =
//        if (totalBalance > 0) (totalExpense / totalIncome * 100).toInt() else 0
//
//    val withFraction = spendingPercentage.coerceIn(0, 100) / 100f
//
//    Box(
//        modifier = Modifier.fillMaxSize()
//            .background(colorResource(R.color.main_color))
//            .padding(16.dp)
//
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(colorResource(R.color.main_color))
//                .padding(16.dp)
//        ) {
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    "Hi, Welcome Back",
//                    fontSize = 20.sp,
//                    color = Color(0xFF052224),
//                    fontWeight = FontWeight.Bold
//                )
//
//                IconButton(onClick = { TODO }) {
//                    Icon(
//                        imageVector = Icons.Default.Notifications,
//                        contentDescription = "Notification"
//                    )
//                }
//            }
//
//            Text(
//                "Good Morning",
//                fontSize = 12.sp,
//                color = Color(0xFF052224),
//                fontWeight = FontWeight.Normal
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Card(
//                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(16.dp),
//                backgroundColor = colorResource(R.color.main_color),
//                border = null
//            ) {
//                Column(
//                    modifier = Modifier.padding(16.dp),
//                    verticalArrangement = Arrangement.SpaceBetween,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Column(horizontalAlignment = Alignment.Start) {
//                            Text(
//                                "Total Balance", fontSize = 12.sp,
//                                fontWeight = FontWeight.Normal,
//                                color = colorResource(R.color.letters)
//                            )
//
//                            Text(
//                                "$${totalIncome - totalExpense}",
//                                fontSize = 24.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = Color.White
//                            )
//                        }
//
//                        Column(horizontalAlignment = Alignment.End) {
//                            Text(
//                                "Total Expense",
//                                fontSize = 12.sp,
//                                fontWeight = FontWeight.Normal
//                            )
//                            Text(
//                                "-$${totalExpense}",
//                                fontSize = 24.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = Color(0xFF0068FF)
//                            )
//                        }
//
//                    }
//
//                    Spacer(modifier = Modifier.height(24.dp))
//
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.Center
//                        ) {
//
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxWidth(0.90f)
//                                    .height(16.dp)
//                                    .background(
//                                        Color.White,
//                                        RoundedCornerShape(4.dp)
//                                    )
//                                    .padding(end = 4.dp),
//                            ) {
//                                Text(
//                                    text = "$spendingPercentage",
//                                    fontSize = 12.sp,
//                                    color = Color.White,
//                                    fontWeight = FontWeight.Bold
//                                )
//
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxWidth(withFraction) // 30% progress bar fill
//                                        .height(16.dp)
//                                        .background(Color(0xFF052224), RoundedCornerShape(4.dp)),
//                                    //contentAlignment = Alignment.Start
//                                )
//
//                                Text(
//                                    text = "$20,000.00",
//                                    fontSize = 12.sp,
//                                    color = Color.Black,
//                                    fontWeight = FontWeight.Bold,
//                                    textAlign = TextAlign.End
//                                )
//
//                            }
//                        }
//
//                        Spacer(modifier = Modifier.height(16.dp))
//
//
//                    }
//                }
//
//            }
//        }
//
//        Box(
//            modifier = Modifier.fillMaxHeight()
//                .background(
//                    color = colorResource(R.color.light_green),
//                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
//                )
//        ) {
//
//            Column(
//                modifier = Modifier.fillMaxHeight()
//            ) {
//
//            }
//        }
//
//    }
//}
//
//@Composable
//fun TransactionList(transactions: List<TransactionEntity>) {
//
//    LazyColumn {
//        items(transactions.size) {transaction ->
//            Card(
//                shape = RoundedCornerShape(12.dp),
//                backgroundColor = Color.White,
//                modifier = Modifier.padding(4.dp)
//            ) {
//
//                Row(
//                    modifier = Modifier.fillMaxWidth().padding(16.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Column {
//                        Text(transactions.title, fontWeight = )
//                    }
//                }
//            }
//        }
//    }
//}
