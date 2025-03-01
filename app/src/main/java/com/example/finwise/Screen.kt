package com.example.finwise

sealed class Screen(val route: String) {
    object LaunchA : Screen("launchA")
    object LaunchB : Screen("launchB")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Home : Screen("home")
    object Notifications : Screen("notifications")
    object AccountBalance : Screen("account_balance")
    object Analysis : Screen("analysis")
}