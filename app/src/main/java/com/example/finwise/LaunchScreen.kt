package com.example.finwise

import android.R.attr.onClick
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun LaunchScreenA(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate("launchB"){
            popUpTo("launchA"){
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF00D09E)),
        contentAlignment = Alignment.Center
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_fin_wise),
                contentDescription = "FinWise Logo"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "FinWise",
                fontSize = 52.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}

@Composable
fun LaunchScreenB(navController: NavController){

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Image(
                painter = painterResource(id = R.drawable.ic_logo2),
                contentDescription = "FinWise Logo"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "FinWise",
                fontSize = 52.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.main_color)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Track your expense and send your money wisely",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(modifier = Modifier.width(200.dp).padding(horizontal = 32.dp,).height(52.dp),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.main_color)),
                shape = ButtonDefaults.shape,
                onClick = { navController.navigate("login") }) {
                Text(
                    color = colorResource(R.color.letters),
                    text = "Log In"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(modifier = Modifier.width(200.dp).padding(horizontal = 32.dp,).height(52.dp),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.light_green)),
                shape = ButtonDefaults.shape,
                onClick = { navController.navigate("signup") }) {
                Text(
                    color = colorResource(R.color.dark_mode_green),
                    text = "Sign Up"
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Forgot Password?",
                color = colorResource(R.color.letters),
                modifier = Modifier.clickable() { /* Handle forgot password */ }
            )
        }
    }
}
