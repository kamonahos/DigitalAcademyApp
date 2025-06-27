package com.example.digitalacademyapp.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalacademyapp.userManager.UserManager
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val userManager = UserManager(application)

    fun login(username: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = userManager.loginUser(username, password)
            onResult(success)
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            userManager.registerUser(username, password)
        }
    }
}