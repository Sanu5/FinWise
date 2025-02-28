package com.example.finwise

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController =  rememberNavController()

    NavHost(navController = navController, startDestination = Screen.LaunchA.route){
        composable(Screen.LaunchA.route){ LaunchScreenA(navController) }
        composable(Screen.LaunchB.route){ LaunchScreenB(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.SignUp.route) { SignupScreen(navController) }
//        composable(Screen.Home.route) { HomeScreen(navController) }
//        composable(Screen.Notifications.route) { NotificationsScreen(navController) }
//        composable(Screen.AccountBalance.route) { AccountBalanceScreen(navController) }
//        composable(Screen.Analysis.route) { AnalysisScreen(navController) }
    }
}