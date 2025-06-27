package com.example.digitalacademyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.digitalacademyapp.ui.theme.DigitalAcademyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigitalAcademyAppTheme {
                AppEntryPoint()
            }
        }
    }
}

@Composable
fun AppEntryPoint() {
    var screen by remember { mutableStateOf("home") }

    when (screen) {
        "home" -> HomeScreen(
            onLoginClicked = { screen = "login" },
            onRegisterClicked = { screen = "register" }
        )
        "login" -> LoginScreen(onLoginSuccess = { screen = "main" })
        "register" -> RegisterScreen(onRegisterSuccess = { screen = "main" })
        "main" -> androidx.compose.material3.Text(
            "Welcome to the app!",
            modifier = androidx.compose.ui.Modifier.padding(32.dp)
        )
    }
}

@Composable
fun HomeScreen(
    onLoginClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
) {
    androidx.compose.foundation.layout.Column(
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        androidx.compose.material3.Button(onClick = onLoginClicked) {
            androidx.compose.material3.Text(text = "Login")
        }
        androidx.compose.foundation.layout.Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))
        androidx.compose.material3.Button(onClick = onRegisterClicked) {
            androidx.compose.material3.Text(text = "Register")
        }
    }
}